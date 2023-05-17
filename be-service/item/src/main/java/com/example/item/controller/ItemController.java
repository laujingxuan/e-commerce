package com.example.item.controller;

import com.example.item.DTO.ItemDTO;
import com.example.item.entity.Item;
import com.example.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getAllItems(){
        return ResponseEntity.ok().body(itemService.findAll());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItem(@PathVariable int id){
        ItemDTO item = itemService.findById(id);
        if (item != null){
            return ResponseEntity.ok().body(item);
        }
        return ResponseEntity.notFound().build();
    }
}
