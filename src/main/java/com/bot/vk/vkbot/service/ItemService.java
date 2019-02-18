package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.Item;
import java.util.List;

public interface ItemService {
    Item addItem(Item item);
    void delete(long id);
    Item getById(long id);
    List<Item> getAll();
    List<Item> getByType(long type);
    List<Item> getByUserId(long userId);
}
