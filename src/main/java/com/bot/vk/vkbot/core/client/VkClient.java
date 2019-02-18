package com.bot.vk.vkbot.core.client;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.Photo;

import java.util.List;

public interface VkClient {

    //public
        //effective
        List<Message> readMessages(); //реализовано

        void replyForMessages(List<Message> messages); //частично реализовано (буду доделывать)

        List<Message> sendMessagesRestrictor(List<Message> messages); //можно дать как таску ()
                                                                    // на входе лист с сообщениями (одинаковые для разных user id)
                                                                    //отправка первых 100 сообщений юзерам (ограничение в 100) одним запросом (использовать private sendMessages, кидая туда лист из 100 сообщений )
                                                                    //возвращает лист с сообщениями кроме первых 100
                                                                    // взял первые 100 -> отправил -> вернул оставшиеся

        //not effective
        void sendMessage(String message, int id); //реализовано

        void postProduct(String name, String description, int categoryId, double price, Photo photo);//частично реализовано (буду доделывать)

        void deleteProduct(int id); //можно дать как таску ()

        void banUser(int id); //можно дать как таску ()

        void unBanUser(int id); //можно дать как таску ()

    //private
        //effective
        void sendMessages(List<Message> messages); //вызывается в sendMessagesRestrictor

}
