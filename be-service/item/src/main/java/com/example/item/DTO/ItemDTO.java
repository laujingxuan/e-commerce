package com.example.item.DTO;

import com.example.item.entity.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ItemDTO {

    private int id;

    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal price;

    @NotEmpty
    private String description;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    @NotEmpty
    private String userUuid;

    @Min(value = 1, message = "Value must be greater than or equal to 1")
    private int itemTypeId;

    private String itemTypeName;

    public ItemDTO() {
    }

    public ItemDTO(String name, BigDecimal price, String description, String userUuid, int itemTypeId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.userUuid = userUuid;
        this.itemTypeId = itemTypeId;
    }

    public ItemDTO(String name, BigDecimal price, String description, Timestamp createdTime, Timestamp updatedTime, String userUuid, int itemTypeId, String itemTypeName) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.userUuid = userUuid;
        this.itemTypeId = itemTypeId;
        this.itemTypeName = itemTypeName;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", userUuid='" + userUuid + '\'' +
                ", itemTypeId=" + itemTypeId +
                ", itemTypeName='" + itemTypeName + '\'' +
                '}';
    }
}
