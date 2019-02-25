package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(Long type);

    List<Item> findByUserId(Long userId);


    @Query(value = "select i.* from Item i join Type t on i.id_type = t.id where LOWER(i.name) like %?1% or LOWER(i.description) like %?1% or LOWER(t.name) like %?1% limit 10", nativeQuery=true)
    List<Item> findByString(String line);
}