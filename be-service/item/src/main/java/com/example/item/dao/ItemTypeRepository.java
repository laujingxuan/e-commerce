package com.example.item.dao;

import com.example.item.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {

    ItemType findByName(String name);
}
