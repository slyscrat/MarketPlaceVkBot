package com.bot.vk.vkbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MessageId implements Serializable{
    @Column(name = "id_user")
    private Long userId;

    @Column(name = "date") @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
