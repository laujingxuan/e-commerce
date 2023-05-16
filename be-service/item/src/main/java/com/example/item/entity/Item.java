package com.example.item.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotEmpty
    @Column(name="name", unique = true)
    private String name;

    @NotNull
    @Column(name="price", scale = 2)
    //@Column(precision = 8, scale = 2), which specifies that the column should have a precision of 8 digits in total, with 2 decimal places
    private BigDecimal price;

    @NotEmpty
    @Column(name="description")
    private String description;

    @NotNull
    @Column(name="created_time")
    private Timestamp createdTime;

    @NotNull
    @Column(name="updated_time")
    private Timestamp updatedTime;

    @NotEmpty
    @Column(name="user_uuid")
    private String userUuid;

    @ManyToOne
    @JoinColumn(name="item_type_id")
    private ItemType itemType;

    public Item() {
    }

    public Item(String name, BigDecimal price, String description, String userUuid, ItemType itemType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdTime = Timestamp.valueOf(LocalDateTime.now());
        this.updatedTime = Timestamp.valueOf(LocalDateTime.now());
        this.userUuid = userUuid;
        this.itemType = itemType;
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

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", userUuid='" + userUuid + '\'' +
                ", itemType=" + itemType.getName() +
                '}';
    }
}
