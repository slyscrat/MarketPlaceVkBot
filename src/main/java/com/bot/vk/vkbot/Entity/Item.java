package com.bot.vk.vkbot.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item {

    @Id
    private int itemId;

    @Column
    private int userId;

    @Column
    private String description;

    @Column
    private String picture;

    @Column
    private float price;

    @Column
    private Boolean isSold;

    @OneToMany(mappedBy = "type")
    private Set<Type> typeId;

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

    public Set<Type> getTypeId() {
        return typeId;
    }

    public void setTypeId(Set<Type> typeId) {
        this.typeId = typeId;
    }
}
