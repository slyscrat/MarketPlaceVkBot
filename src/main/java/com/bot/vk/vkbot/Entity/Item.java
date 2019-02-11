package com.bot.vk.vkbot.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @Column(name = "ITEM_ID")
    private int itemId;

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PICTURE")
    private String picture;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "IS_SOLD")
    private Boolean isSold;

    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private int typeId;

    public Item(){

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
