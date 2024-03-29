package com.example.item.service;

import com.example.item.DTO.ItemDTO;
import com.example.item.dao.ItemRepository;
import com.example.item.dao.ItemTypeRepository;
import com.example.item.entity.Item;
import com.example.item.entity.ItemType;
import com.example.item.kafka.ActionKafkaUtils;
import com.example.item.modelMapper.ItemMapper;
import com.example.shared.enums.ActionOnItem;
import com.example.shared.enums.Role;
import com.example.shared.exception.CustomNotFoundException;
import com.example.shared.exception.CustomUnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private ItemRepository itemRepository;

    private ItemMapper itemMapper;

    private ItemTypeRepository itemTypeRepository;

    private ActionKafkaUtils actionKafkaUtils;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, ItemTypeRepository itemTypeRepository, ActionKafkaUtils actionKafkaUtils) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemTypeRepository = itemTypeRepository;
        this.actionKafkaUtils = actionKafkaUtils;
    }

    @Override
    public ItemDTO findById(int id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            throw new CustomNotFoundException("Item of id " + String.valueOf(id) + " is not found");
        }
        ItemDTO itemDTO = itemMapper.mapToDTO(item.get());
        return itemDTO;
    }

    @Override
    public ItemDTO findByName(String name) {
        Item item = itemRepository.findByName(name);
        if (item == null) {
            throw new CustomNotFoundException("Item of name " + name + " is not found");
        }
        return itemMapper.mapToDTO(item);
    }

    @Override
    public ItemDTO findByNameAndUserUuid(String name, String userUuid) {
        Item item = itemRepository.findByNameAndUserUuid(name, userUuid);
        if (item == null) {
            throw new CustomNotFoundException("Item of name " + name + " and userUuid " + userUuid + " is not found");
        }
        return itemMapper.mapToDTO(item);
    }

    @Override
    public List<ItemDTO> findByItemTypeName(String itemTypeName) {
        List<Item> itemList = itemRepository.findByItemTypeName(itemTypeName);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDTO = itemMapper.mapToDTO(item);
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public List<ItemDTO> findAll() {
        List<Item> itemList = itemRepository.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDTO = itemMapper.mapToDTO(item);
            itemDTOList.add(itemDTO);
        }
        return itemDTOList;
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO) {
        Item item = itemMapper.mapToEntity(itemDTO);
        ItemType itemType = getItemTypeById(itemDTO.getItemTypeId());
        validateUniqueItemName(item.getName());
        item.setItemType(itemType);
        item.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        Item createdItem = itemRepository.save(item);
        sendMsgToUserAction(item, ActionOnItem.CREATE_ITEM);
        itemDTO = itemMapper.mapToDTO(createdItem);
        return itemDTO;
    }

    public void sendMsgToUserAction(Item item, ActionOnItem actionOnItem) {
        actionKafkaUtils.sendMessage(actionOnItem, item.getId(), item.getUserUuid());
    }

    @Override
    public ItemDTO update(String authority, ItemDTO itemDTO) {
        //Not able to update userUuid and createdTime fields
        Item item = getItemById(itemDTO.getId());
        validateUserAuthority(authority, itemDTO.getUserUuid(), item.getUserUuid());

        ItemType itemType = getItemTypeById(itemDTO.getItemTypeId());
        item.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setItemType(itemType);
        Item updatedItem = itemRepository.save(item);
        sendMsgToUserAction(updatedItem, ActionOnItem.UPDATE_ITEM);
        itemDTO = itemMapper.mapToDTO(updatedItem);
        return itemDTO;
    }

    @Override
    public boolean deleteById(String authority, String userUuid, int id) {
        Item item = getItemById(id);
        validateUserAuthority(authority, userUuid, item.getUserUuid());
        itemRepository.deleteById(id);
        sendMsgToUserAction(item, ActionOnItem.DELETE_ITEM);
        return true;
    }

    private ItemType getItemTypeById(int id) {
        Optional<ItemType> itemTypeOptional = itemTypeRepository.findById(id);
        if (!itemTypeOptional.isPresent()) {
            throw new IllegalArgumentException("itemType not found");
        }
        return itemTypeOptional.get();
    }

    private Item getItemById(int id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (!itemOptional.isPresent()) {
            throw new IllegalArgumentException("itemType not found");
        }
        return itemOptional.get();
    }

    private void validateUniqueItemName(String itemName) {
        Item existingItem = itemRepository.findByName(itemName);
        if (existingItem != null) {
            throw new IllegalArgumentException("Non-unique item name");
        }
    }

    private void validateUserAuthority(String authority, String requestUserUuid, String dbUserUuid) {
        if (Role.valueOf(authority) != Role.ROLE_ADMIN && !requestUserUuid.equals(dbUserUuid)) {
            throw new CustomUnauthorizedException("item is not belong to user");
        }
    }
}
