package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
