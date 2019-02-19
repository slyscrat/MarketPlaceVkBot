package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.MessageId;
import com.bot.vk.vkbot.Entity.Messages;
import com.bot.vk.vkbot.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {
    private MessagesRepository messagesRepository;

    @Autowired
    public MessagesService(MessagesRepository messagesRepository){
        this.messagesRepository = messagesRepository;
    }



    public void create(Messages messages) {
        this.messagesRepository.save(messages);
    }


    public void delete(MessageId messageId) {
        this.messagesRepository.deleteByMessageId(messageId);
    }


    public void clear() {
        this.messagesRepository.deleteAll();
    }


    public Messages getMessageById(MessageId messageId) {
        return this.messagesRepository.getByMessageId(messageId);
    }


    public List<Messages> getAll() {
        List<Messages> list = new ArrayList<>();
        this.messagesRepository.findAll().forEach(list::add);
        return list;
    }
}
