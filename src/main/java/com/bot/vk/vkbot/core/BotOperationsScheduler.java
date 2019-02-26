package com.bot.vk.vkbot.core;

import com.bot.vk.vkbot.core.client.VkClient;
import com.vk.api.sdk.objects.messages.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class BotOperationsScheduler {

    private final VkClient vkClient;
    private int counter;

    @Autowired
    public BotOperationsScheduler(VkClient vkClient) {
        this.vkClient = vkClient;
        this.counter = 0;
    }

    @Scheduled(fixedDelayString = "${bot.operations_interval}")
    public void scheduleFixedDelayTask() {
        log.info("This bot is alive " + counter++ + " sec");
        List<Message> messages = vkClient.readMessages();
        vkClient.replyForMessages(messages);
    }

    @Scheduled(fixedDelayString = "${bot.item_post_interval}")
    public void postNewestProducts() throws Exception {
        log.info("Posting latest product");
        this.vkClient.postWall();
    }
}