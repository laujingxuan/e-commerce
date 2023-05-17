package com.example.item.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ItemTypeDTO {

    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private List<ItemDTO> itemList;

    public ItemTypeDTO() {
    }

    public ItemTypeDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ItemTypeDTO(String name, String description, List<ItemDTO> itemList) {
        this.name = name;
        this.description = description;
        this.itemList = itemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemDTO> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "ItemTypeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
