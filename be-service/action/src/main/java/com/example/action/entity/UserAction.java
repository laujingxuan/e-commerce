package com.example.action.entity;

import com.example.action.common.enums.ActionOnItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name="user-action")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name="user_uuid")
    private String userUuid;


    @Min(value=1, message="itemId cannot be empty")
    @Column(name="item_id")
    private int itemId;

    @NotNull
    @Column(name="action_on_item")
    private ActionOnItem actionOnItem;

    @NotNull
    @Column(name="action_time")
    private Timestamp actionTime;

    public UserAction() {
    }

    public UserAction(String userUuid, int itemId, ActionOnItem actionOnItem) {
        this.userUuid = userUuid;
        this.itemId = itemId;
        this.actionOnItem = actionOnItem;
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

    public ActionOnItem getActionOnItem() {
        return actionOnItem;
    }

    public void setActionOnItem(ActionOnItem actionOnItem) {
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
        return "UserAction{" +
                "id=" + id +
                ", userUuid='" + userUuid + '\'' +
                ", itemId=" + itemId +
                ", actionOnItem=" + actionOnItem +
                ", actionTime=" + actionTime +
                '}';
    }
}
