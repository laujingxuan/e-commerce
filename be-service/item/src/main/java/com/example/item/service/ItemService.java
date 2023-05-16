package com.example.item.service;

import com.example.item.entity.Item;

import java.util.List;

public interface ItemService {
    boolean save(Item item);

    Item findById(int id);

    Item findByName(String name);

    Item findByNameAndUserUuid(String name, String userUuid);

    List<Item> findByItemTypeName(String itemTypeName);

    List<Item> findAll();
}
