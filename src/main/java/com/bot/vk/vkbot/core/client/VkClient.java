package com.bot.vk.vkbot.core.client;

import com.vk.api.sdk.objects.messages.Message;

import java.util.List;

public interface VkClient {

    void sendMessage(String message, int id); //+

    List<Message> readMessages(); //+

    void replyForMessages(List<Message> messages); //?

    int getMembersCount(); //+

    List<Integer> getMembers(); //+

    void postProduct(Long userId, String name, String description, Long type, Float price, Integer pictureId);

    void deleteProduct(Long Id);

    void banUser();

    void unBanUser();

    //need to complete
}
