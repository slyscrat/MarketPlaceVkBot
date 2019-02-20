package com.bot.vk.vkbot.core.client;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.Photo;

import java.util.List;

public interface VkClient {

    //public
    //effective
    List<Message> readMessages(); //реализовано

    void replyForMessages(List<Message> messages); //частично реализовано (буду доделывать)

    List<Message> sendMessagesRestrictor(List<Message> messages);
    // на входе лист с сообщениями (одинаковые для разных user id)
    //отправка первых 100 сообщений юзерам (ограничение в 100) одним запросом (использовать private sendMessages, кидая туда лист из 100 сообщений )
    //возвращает лист с сообщениями кроме первых 100
    // взял первые 100 -> отправил -> вернул оставшиеся

    //not effective
    void sendMessage(String message, int id); //реализовано

    void postProduct(Long userId, String name, String description, int categoryId, Long type, Float price, Photo photo);

    void postProduct(String name, String description, int categoryId, double price, Photo photo);

    void deleteProduct(Long Id);

    void banUser(int id);

    void unBanUser(int id);

    //private
    //effective
    void sendMessages(List<Message> messages); //вызывается в sendMessagesRestrictor

}
