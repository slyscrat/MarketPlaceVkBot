package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.MessageId;
import com.bot.vk.vkbot.Entity.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Messages, Long> {
    void deleteByMessageId(MessageId messageId);
    Messages getByMessageId(MessageId messagesId);
}
