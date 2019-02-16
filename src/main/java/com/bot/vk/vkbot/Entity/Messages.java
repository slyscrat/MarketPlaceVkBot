package com.bot.vk.vkbot.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Messages")
public class Messages {

    @EmbeddedId
    private MessageId messageId;
    private String message;
    @Column(name = "id_item")
    private Integer itemId;

    public Messages(){}

    public Messages(MessageId messageId, String message){
        this.messageId = messageId;
        this.message = message;
    }

    public Messages(MessageId messageId, String message, Integer itemId){
        this.messageId = messageId;
        this.message = message;
        this.itemId = itemId;
    }
}
