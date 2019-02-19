package com.bot.vk.vkbot.core;

import com.bot.vk.vkbot.core.client.VkClient;
import com.bot.vk.vkbot.service.ItemService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Dialog;
import com.vk.api.sdk.objects.messages.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bot.vk.vkbot.Entity.Item;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Log4j2
public class VkClientImpl implements VkClient {

    private final VkApiClient apiClient = new VkApiClient(HttpTransportClient.getInstance());
    private GroupActor actor;

    private final ItemService itemService;

    @Value("${bot.group.id}")
    private int groupID;

    @Value("${bot.group.client_secret}")
    private String token;

    @Autowired
    public VkClientImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostConstruct
    public void init() {
        this.actor = new GroupActor(groupID, token);
    }

    @Override
    public void sendMessage(String message, int userId) {
        Random random = new Random();
        try {
            this.apiClient.messages().send(actor).message(message).userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
    }

    @Override
    public List<Message> readMessages() {
        List<Message> result = new ArrayList<>();
        try {
            List<Dialog> dialogs = apiClient.messages().getDialogs(actor).unanswered1(true).execute().getItems();
            for (Dialog item : dialogs) {
                result.add(item.getMessage());
            }
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
        return result;
    }

    @Override
    public void replyForMessages(List<Message> messages) {
        String body;
        for (Message item : messages) {
            body = item.getBody();
            sendMessage("You tap: " + body, item.getUserId());
        }
    }

    @Override
    public int getMembersCount() {
        int result = 0;
        try {
            result = apiClient.groups().getMembers(actor).groupId(actor.getGroupId().toString()).execute().getCount();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
        return result;
    }


    @Override
    public List<Integer> getMembers() {
        List<Integer> result = null;
        try {
            result = apiClient.groups().getMembers(actor).groupId(actor.getGroupId().toString()).execute().getItems();
        } catch (ApiException | ClientException e) {
            log.error(e);
        }
        return result;
    }

    @Override
    public void postProduct(Long userId, String name, String description, Long type, Float price, Integer pictureId) {
        try{
            this.itemService.addItem(new Item(userId, name, description, pictureId, price, type));
        }
        catch(Exception ex){
            log.error(ex);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        try{
            this.itemService.delete(id);
        }
        catch(Exception ex){
            log.error(ex);
        }
        //delete
        //apiClient.market().getById()
    }

    @Override
    public void banUser() {
        //ban
    }

    @Override
    public void unBanUser() {
        //unban
    }
}