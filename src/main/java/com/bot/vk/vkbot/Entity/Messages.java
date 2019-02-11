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
}
