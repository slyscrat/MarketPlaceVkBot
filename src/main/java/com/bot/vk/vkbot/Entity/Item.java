package com.bot.vk.vkbot.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Item")
public class Item{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user")
    private Long userId;
    private String name;
    private String description;
    @Column(name = "id_picture")
    private Integer pictureId;
    private Float price;
    private Boolean isSold = false;
    @Column(name = "id_type")
    private Long type;

    public Item(){}

    public Item(Long userId, String name, String description, Integer pictureId, Float price, Long type) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.pictureId = pictureId;
        this.price = price;
        this.type = type;
    }

}
