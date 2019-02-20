package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot._entity.MessageId;
import com.bot.vk.vkbot._entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, Long> {
    void deleteByMessageId(MessageId messageId);
    Message getByMessageId(MessageId messagesId);
}
