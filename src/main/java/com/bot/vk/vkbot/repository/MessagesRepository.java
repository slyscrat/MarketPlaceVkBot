package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer>{
}
