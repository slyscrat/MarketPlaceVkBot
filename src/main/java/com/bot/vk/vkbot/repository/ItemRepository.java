package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(Long type);

    List<Item> findByUserId(Long userId);
}
