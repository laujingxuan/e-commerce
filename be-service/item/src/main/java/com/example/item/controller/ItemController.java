package com.example.item.controller;

import com.example.item.DTO.ItemDTO;
import com.example.item.service.ItemService;
import com.example.shared.utils.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    private JwtTokenService jwtTokenService;

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService, JwtTokenService jwtTokenService){
        this.jwtTokenService = jwtTokenService;
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
    public ResponseEntity<Void> createItem(HttpServletRequest request, @Valid @RequestBody ItemDTO itemDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            logger.warn("Failed binding result: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(request);
        if (!jwtTokenService.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // update the userDTO with the user uuid
        itemDTO.setUserUuid(jwtTokenService.extractUserUuid(jwtToken));
        ItemDTO createdItem = itemService.create(itemDTO);
        if (createdItem != null){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/items")
    public ResponseEntity<Void> updateItem(HttpServletRequest request, @Valid @RequestBody ItemDTO itemDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            logger.warn("Failed binding result: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(request);
        if (!jwtTokenService.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userUuid = jwtTokenService.extractUserUuid(jwtToken);
        itemDTO.setUserUuid(userUuid);
        String authority = jwtTokenService.extractAuthority(jwtToken);

        ItemDTO updatedItem = itemService.update(authority, itemDTO);
        if (updatedItem != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(HttpServletRequest request, @PathVariable int id){
        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(request);
        if (!jwtTokenService.validateToken(jwtToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String authority = jwtTokenService.extractAuthority(jwtToken);
        String userUuid = jwtTokenService.extractUserUuid(jwtToken);
        boolean isDeleted = itemService.deleteById(authority, userUuid, id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
