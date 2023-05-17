package com.example.item.service;

import com.example.item.DTO.ItemTypeDTO;
import com.example.item.entity.ItemType;

public interface ItemTypeService {
    ItemTypeDTO create(ItemTypeDTO ItemTypeDTO);

    ItemTypeDTO findById(int id);

    ItemTypeDTO findByName(String name);
}
