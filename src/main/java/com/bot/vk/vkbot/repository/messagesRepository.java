package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface messagesRepository extends JpaRepository<messages, Integer>{
}
