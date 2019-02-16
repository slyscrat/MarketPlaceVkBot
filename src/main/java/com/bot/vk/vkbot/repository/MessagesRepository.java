package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Messages, Long> {
}
