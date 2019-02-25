package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(Long type);

    List<Item> findByUserId(Long userId);

    @Query("select i from Item i join Type t on i.type = t.id where lower(i.name) like %?1% or lower(i.description) like %?1% or lower(t.name) like %?1%")
    List<Item> findByString(String line);
}