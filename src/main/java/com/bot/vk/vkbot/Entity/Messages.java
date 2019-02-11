package com.bot.vk.vkbot.Entity;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "MESSAGES")
public class Messages {

    @Id
    @Column(name = "DATE")
    private Date date;

    @Id
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "MESSAGE")
    private String message;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private int itemId;

    public Messages(){

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
