package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query("select i from Item i inner join Type t on i.type = t.id where t.id = ?1")
    List<Item> findByTypeId(long type);

    @Query("select i from Item i where i.user = ?1")
    List<Item> findByUser(long user);
}
