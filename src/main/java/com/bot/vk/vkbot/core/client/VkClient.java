package com.bot.vk.vkbot.core.client;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.Photo;

import java.util.List;

public interface VkClient {

    List<Message> readMessages();

    void replyForMessages(List<Message> messages);

    void sendMessage(String message, int userId);

    void sendMessage(String message, List<String> attachment, int userId);

	int postProduct(Long userId, String name, String description, Long type, Float price, Photo photo);

    void deleteProduct(Long Id);

    void banUser(int id);

    String getMarketInfo(String command);

    void postWall();

    List<Message> sendMessagesRestrictor(List<Message> messages); // not yet

    void sendMessages(List<Message> messages); //вызывается в sendMessagesRestrictor (not yet)
}
