package com.example.user.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public class UserActionDTO {

    private int id;

    @NotEmpty
    private String userUuid;

    @Min(value=1, message="itemId cannot be empty")
    private int itemId;

    @NotNull
    private String actionOnItem;

    private Timestamp actionTime;

    public UserActionDTO() {
    }

    public UserActionDTO(String userUuid, int itemId, String actionOnItem, Timestamp actionTime) {
        this.userUuid = userUuid;
        this.itemId = itemId;
        this.actionOnItem = actionOnItem;
        this.actionTime = actionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getActionOnItem() {
        return actionOnItem;
    }

    public void setActionOnItem(String actionOnItem) {
        this.actionOnItem = actionOnItem;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    @Override
    public String toString() {
        return "UserActionDTO{" +
                "id=" + id +
                ", userUuid='" + userUuid + '\'' +
                ", itemId=" + itemId +
                ", actionOnItem=" + actionOnItem +
                ", actionTime=" + actionTime +
                '}';
    }
}