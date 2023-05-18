package com.example.item.service;

import com.example.item.DTO.ItemDTO;
import com.example.item.ModelMapper.ItemMapper;
import com.example.item.dao.ItemRepository;
import com.example.item.dao.ItemTypeRepository;
import com.example.item.entity.Item;
import com.example.item.entity.ItemType;
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

    private ItemRepository itemRepository;

    private ItemMapper itemMapper;

    private ItemTypeRepository itemTypeRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, ItemTypeRepository itemTypeRepository) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemTypeRepository = itemTypeRepository;
    }

    @Override
    public ItemDTO create(ItemDTO itemDTO) {
        try {
            Item item = itemMapper.mapToEntity(itemDTO);
            Optional<ItemType> itemType = itemTypeRepository.findById(itemDTO.getItemTypeId());
            if (!itemType.isPresent()) {
                throw new IllegalArgumentException("itemType not found");
            }
            //TODO: Should set item UUID from the user info retrieved from JWT token
            item.setItemType(itemType.get());
            item.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
            Item createdItem = itemRepository.save(item);
            itemDTO = itemMapper.mapToDTO(createdItem);
            return itemDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ItemDTO update(ItemDTO itemDTO) {
        //Not able to update userUuid and createdTime fields
        try {
            Optional<Item> itemOptional = itemRepository.findById(itemDTO.getId());
            if (!itemOptional.isPresent()){
                throw new IllegalArgumentException("item not found");
            }
            Item item = itemOptional.get();

            Optional<ItemType> itemType = itemTypeRepository.findById(itemDTO.getItemTypeId());
            if (!itemType.isPresent()){
                throw new IllegalArgumentException("itemType not found");
            }
            item.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setPrice(itemDTO.getPrice());
            item.setItemType(itemType.get());
            Item updatedItem = itemRepository.save(item);
            itemDTO = itemMapper.mapToDTO(updatedItem);
            return itemDTO;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try {
            if (!itemRepository.findById(id).isPresent()){
                throw new IllegalArgumentException("Item not found");
            }
            itemRepository.deleteById(id);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ItemDTO findById(int id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            ItemDTO itemDTO = itemMapper.mapToDTO(item.get());
            return itemDTO;
        }
        return null;
    }

    @Override
    public ItemDTO findByName(String name) {
        Item item = itemRepository.findByName(name);
        return itemMapper.mapToDTO(item);
    }

    @Override
    public ItemDTO findByNameAndUserUuid(String name, String userUuid) {
        Item item = itemRepository.findByNameAndUserUuid(name, userUuid);
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
}
