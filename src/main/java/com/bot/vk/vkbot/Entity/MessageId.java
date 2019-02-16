package com.bot.vk.vkbot.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class MessageId implements Serializable{
    @Column(name = "id_user")
    private Long userId;

    @Column(name = "date") @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public MessageId(){}

    public MessageId(Long userId,Date date){
        this.userId = userId;
        this.date = date;
    }
}
