package com.bot.vk.vkbot.service;

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

    private final VkClient client;
    private int counter;

    @Autowired
    public BotOperationsScheduler(VkClient client) {
        this.client = client;
        this.counter = 0;
    }

    @Scheduled(fixedDelayString = "${bot.operations_interval}")
    public void scheduleFixedDelayTask() {
        log.info("This bot is alive " + counter++ + " sec");
        List<Message> messages = client.readMessages();
        client.replyForMessages(messages);
    }
}