package com.example.shared.enums;

public class KafkaMsg {
    private ActionOnItem actionOnItem;

    private int itemId;

    private String userUuid;

    public KafkaMsg() {
    }

    public KafkaMsg(ActionOnItem actionOnItem, int itemId, String userUuid) {
        this.actionOnItem = actionOnItem;
        this.itemId = itemId;
        this.userUuid = userUuid;
    }

    public ActionOnItem getActionOnItem() {
        return actionOnItem;
    }

    public void setActionOnItem(ActionOnItem actionOnItem) {
        this.actionOnItem = actionOnItem;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public String toString() {
        return "KafkaMsg{" +
                "actionOnItem=" + actionOnItem +
                ", itemId=" + itemId +
                ", userUuid='" + userUuid + '\'' +
                '}';
    }
}
