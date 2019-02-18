package com.bot.vk.vkbot.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Type")
public class Type {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Type(){}

    public Type(String name) {
        this.name = name;
    }
}
