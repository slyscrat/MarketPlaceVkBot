package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot._entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
