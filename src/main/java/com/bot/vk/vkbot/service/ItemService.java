package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.Item;
import java.util.List;

public interface ItemService {
    Item addItem(Item item);
    void delete(Long id);
    Item getById(Long id);
    List<Item> getAll();
    List<Item> getByType(Long type);
    List<Item> getByUserId(Long userId);
}