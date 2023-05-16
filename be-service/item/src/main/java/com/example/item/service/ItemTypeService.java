package com.example.item.service;

import com.example.item.entity.ItemType;

public interface ItemTypeService {
    boolean save(ItemType itemType);

    ItemType findByName(String name);
}
