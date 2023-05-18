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
        List<ItemDTO> itemDTOS = itemService.findAll();
        return ResponseEntity.ok().body(itemDTOS);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItem(@PathVariable int id){
        ItemDTO itemDTO = itemService.findById(id);
        if (itemDTO != null){
            return ResponseEntity.ok().body(itemDTO);
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
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            System.out.println("Failed binding result");
            return ResponseEntity.badRequest().build();
        }
        ItemDTO updatedItem = itemService.update(itemDTO);
        if (updatedItem != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id){
        boolean isDeleted = itemService.deleteById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
