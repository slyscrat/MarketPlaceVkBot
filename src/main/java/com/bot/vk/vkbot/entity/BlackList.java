package com.bot.vk.vkbot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BlackList")
@Data
@NoArgsConstructor
public class BlackList{

    @Id
    @Column(name = "id_user")
    private Long id;

    private Integer warnings = 0;

    public BlackList(Long userId) {
        this.id = userId;
    }
}

