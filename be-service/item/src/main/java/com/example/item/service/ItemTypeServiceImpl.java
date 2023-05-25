package com.example.item.service;

import com.example.item.DTO.ItemTypeDTO;
import com.example.item.modelMapper.ItemTypeMapper;
import com.example.item.dao.ItemTypeRepository;
import com.example.item.entity.ItemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ItemTypeServiceImpl implements ItemTypeService{

    private Logger logger = LoggerFactory.getLogger(ItemTypeServiceImpl.class);

    private ItemTypeRepository itemTypeRepository;

    private ItemTypeMapper itemTypeMapper;

    @Autowired
    public ItemTypeServiceImpl(ItemTypeRepository itemTypeRepository, ItemTypeMapper itemTypeMapper){
        this.itemTypeRepository = itemTypeRepository;
        this.itemTypeMapper = itemTypeMapper;
    }

    @Override
    public ItemTypeDTO create(ItemTypeDTO itemTypeDto) {
        try {
            ItemType itemType = itemTypeMapper.mapToEntity(itemTypeDto);
            itemType = itemTypeRepository.save(itemType);
            itemTypeDto = itemTypeMapper.mapToDTO(itemType);
            return itemTypeDto;
        } catch (Exception e){
            logger.error("Error for create itemType", e);
            return null;
        }
    }

    @Override
    public ItemTypeDTO findById(int id) {
        Optional<ItemType> itemType = itemTypeRepository.findById(id);
        if (!itemType.isPresent()){
            return null;
        }
        ItemTypeDTO itemTypeDTO = itemTypeMapper.mapToDTO(itemType.get());
        return itemTypeDTO;
    }

    @Override
    public ItemTypeDTO findByName(String name) {
        ItemType itemType = itemTypeRepository.findByName(name);
        if (itemType == null){
            return null;
        }
        ItemTypeDTO itemTypeDTO = itemTypeMapper.mapToDTO(itemType);
        return itemTypeDTO;
    }
}
