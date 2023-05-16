package com.example.item.service;

import com.example.item.dao.ItemTypeRepository;
import com.example.item.entity.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeServiceImpl implements ItemTypeService{

    private ItemTypeRepository itemTypeRepository;

    @Autowired
    public ItemTypeServiceImpl(ItemTypeRepository itemTypeRepository){
        this.itemTypeRepository = itemTypeRepository;
    }

    @Override
    public boolean save(ItemType itemType) {
        try {
            itemTypeRepository.save(itemType);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ItemType findByName(String name) {
        return itemTypeRepository.findByName(name);
    }
}
