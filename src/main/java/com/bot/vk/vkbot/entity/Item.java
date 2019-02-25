package com.bot.vk.vkbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Item")
@Data
@NoArgsConstructor
public class Item{

    @Id
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

    public Item(Long id, Long userId, String name, String description, Integer pictureId, Float price, Long type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.pictureId = pictureId;
        this.price = price;
        this.type = type;
    }

}
