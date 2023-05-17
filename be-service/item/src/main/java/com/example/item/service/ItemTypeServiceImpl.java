package com.example.item.service;

import com.example.item.DTO.ItemTypeDTO;
import com.example.item.ModelMapper.ItemTypeMapper;
import com.example.item.dao.ItemTypeRepository;
import com.example.item.entity.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ItemTypeServiceImpl implements ItemTypeService{

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
            System.out.println(e.getMessage());
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
