package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query("select i from Item i where i.type = ?1")
    List<Item> findByType(long type);

    @Query("select i from Item i where i.userId = ?1")
    List<Item> findByUserId(long userId);
}
