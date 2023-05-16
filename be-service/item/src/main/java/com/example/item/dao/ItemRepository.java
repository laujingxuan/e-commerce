package com.example.item.dao;

import com.example.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findByName(String name);

    @Query("SELECT i FROM Item i WHERE i.name=:name AND i.userUuid=:userUuid")
    Item findByNameAndUserUuid(@Param("name") String name, @Param("userUuid") String userUuid);

//    @Query("SELECT i FROM Item i JOIN FETCH i.itemType t WHERE t.name=:itemTypeName")
    @Query("SELECT i FROM Item i WHERE i.itemType.name=:itemTypeName")
    List<Item> findByItemTypeName(@Param("itemTypeName") String itemTypeName);
}
