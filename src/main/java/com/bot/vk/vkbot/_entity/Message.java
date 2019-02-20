package com.bot.vk.vkbot._entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Message")
public class Message {

    public Message(MessageId messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }

    @EmbeddedId
    private MessageId messageId;

    private String message;

    @Column(name = "id_item")
    private Integer itemId;
}
