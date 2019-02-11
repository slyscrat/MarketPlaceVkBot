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

    @Autowired
    //it is better not to use private field injection to avoid reflection operations -> replaced with constructor injection
    public BotOperationsScheduler(VkClient client) {
        this.client = client;
    }

    @Scheduled(fixedDelayString = "${bot.operations_interval}")
    public void scheduleFixedDelayTask() {
        log.info("It work!");
        List<Message> messages = client.readMessages();
        client.replyForMessages(messages);
    }
}