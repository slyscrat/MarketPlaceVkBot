package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    void deleteById(Long id);
    Item getById(Long id);
    List<Item> findByType(Long type);

    List<Item> findByUserId(Long userId);
}
