package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.BlackList;
import com.bot.vk.vkbot.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Item>{
}
