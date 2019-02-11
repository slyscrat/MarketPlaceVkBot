package com.bot.vk.vkbot.core.client;

import com.vk.api.sdk.objects.messages.Message;

import java.util.List;

public interface VkClient {

    void sendMessage(String message, int id); //+

    List<Message> readMessages(); //+

    void replyForMessages(List<Message> messages); //?

    int getMembersCount(); //+

    List<Integer> getMembers(); //+

    void postProduct();

    void deleteProduct();

    void banUser();

    void unBanUser();

    //need to complete
}
