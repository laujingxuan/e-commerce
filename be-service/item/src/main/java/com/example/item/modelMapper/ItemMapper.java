package com.example.item.modelMapper;

import com.example.item.DTO.ItemDTO;
import com.example.item.entity.Item;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class ItemMapper {

    private ModelMapper modelMapper;

    @Autowired
    public ItemMapper (ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public Item mapToEntity(ItemDTO itemDTO){
        // The map method does not automatically handle relationships such as one-to-one mapping in entity objects. By default, the ModelMapper library maps properties based on their names and types, but it doesn't automatically handle relationships between objects.
        Item item = modelMapper.map(itemDTO, Item.class);
        item.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        return item;
    }

    public ItemDTO mapToDTO(Item item){
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
        if (item.getItemType() != null){
            itemDTO.setItemTypeId(item.getItemType().getId());
            itemDTO.setItemTypeName(item.getItemType().getName());
        }
        return itemDTO;
    }
}
