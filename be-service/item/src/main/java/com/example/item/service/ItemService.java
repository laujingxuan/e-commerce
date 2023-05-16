package com.example.item.service;

import com.example.item.entity.Item;

public interface ItemService {
    boolean save(Item item);

    Item findByName(String name);
}
