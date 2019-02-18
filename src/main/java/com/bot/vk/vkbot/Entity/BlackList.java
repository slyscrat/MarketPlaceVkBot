package com.bot.vk.vkbot.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BlackList")
public class BlackList{

    @Id @Column(name = "id_user")
    private Long id;
    private Integer warnings;

    public BlackList(){}
    public BlackList(Long id, Integer warnings){
        this.id = id;
        this.warnings = warnings;
    }

}
