package com.example.item.service;

import com.example.item.DTO.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO create(ItemDTO itemDTO);

    ItemDTO update(ItemDTO itemDTO);

    boolean deleteById(int id);

    ItemDTO findById(int id);

    ItemDTO findByName(String name);

    ItemDTO findByNameAndUserUuid(String name, String userUuid);

    List<ItemDTO> findByItemTypeName(String itemTypeName);

    List<ItemDTO> findAll();
}
