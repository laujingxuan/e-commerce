package com.example.item.ModelMapper;

import com.example.item.DTO.ItemDTO;
import com.example.item.DTO.ItemTypeDTO;
import com.example.item.entity.Item;
import com.example.item.entity.ItemType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemTypeMapper {

    private ModelMapper modelMapper;

    private ItemMapper itemMapper;

    @Autowired
    public ItemTypeMapper(ModelMapper modelMapper, ItemMapper itemMapper){
        this.modelMapper = modelMapper;
        this.itemMapper = itemMapper;
    }

    public ItemType mapToEntity(ItemTypeDTO itemTypeDTO){
        ItemType itemType = modelMapper.map(itemTypeDTO, ItemType.class);
        return itemType;
    }

    public ItemTypeDTO mapToDTO(ItemType itemType){
        ItemTypeDTO itemTypeDTO = modelMapper.map(itemType, ItemTypeDTO.class);
        if (itemType.getItemList() != null){
            List<ItemDTO> itemDTOList = new ArrayList<>();
            for (Item item: itemType.getItemList()){
                ItemDTO itemDTO = itemMapper.mapToDTO(item);
                itemDTOList.add(itemDTO);
            }
            itemTypeDTO.setItemList(itemDTOList);
        }
        return itemTypeDTO;
    }
}
