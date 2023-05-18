package com.example.item.controller;

import com.example.item.DTO.ItemDTO;
import com.example.item.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/items")
    public ResponseEntity<Void> createItem(@Valid @RequestBody ItemDTO itemDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("Error: " + bindingResult.getAllErrors());
            System.out.println("Failed binding result");
            return ResponseEntity.badRequest().build();
        }
        ItemDTO createdItem = itemService.create(itemDTO);
        if (createdItem != null){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/items")
    public ResponseEntity<Void> updateItem(@Valid @RequestBody ItemDTO itemDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors() || itemDTO.getId() == 0){
            if (bindingResult.hasErrors()){
                System.out.println(bindingResult.getAllErrors());
            }
            System.out.println("Failed binding result");
            return ResponseEntity.badRequest().build();
        }
        ItemDTO updatedItem = itemService.update(itemDTO);
        if (updatedItem != null){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
