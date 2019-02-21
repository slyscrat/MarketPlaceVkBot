package com.bot.vk.vkbot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BlackList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackList{

    @Id
    @Column(name = "id_user")
    private Long id;

    private Integer warnings;
}

