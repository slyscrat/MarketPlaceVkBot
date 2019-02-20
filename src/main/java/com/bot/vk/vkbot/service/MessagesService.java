package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot._entity.MessageId;
import com.bot.vk.vkbot._entity.Message;
import com.bot.vk.vkbot.repository.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessagesService {

    private final MessagesRepository messagesRepository;

    public void create(Message message) {
        messagesRepository.save(message);
    }

    public void delete(MessageId messageId) {
        messagesRepository.deleteByMessageId(messageId);
    }

    public void clear() {
        messagesRepository.deleteAll();
    }

    public Message getMessageById(MessageId messageId) {
        return messagesRepository.getByMessageId(messageId);
    }

    public List<Message> getAll() {
        return messagesRepository.findAll();
    }
}
