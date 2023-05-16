package com.example.item.service;

import com.example.item.dao.ItemRepository;
import com.example.item.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean save(Item item) {
        try {
            itemRepository.save(item);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public Item findByNameAndUserUuid(String name, String userUuid) {
        return itemRepository.findByNameAndUserUuid(name, userUuid);
    }

    @Override
    public List<Item> findByItemTypeName(String itemTypeName) {
        return itemRepository.findByItemTypeName(itemTypeName);
    }
}
