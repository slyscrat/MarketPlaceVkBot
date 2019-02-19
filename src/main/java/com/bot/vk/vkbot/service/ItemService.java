package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.Item;
import com.bot.vk.vkbot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public void create(Item item) {
        this.itemRepository.save(item);
    }


    public void deleteById(Long id) {
        this.itemRepository.deleteById(id);
    }


    public void clear() {
        this.itemRepository.deleteAll();
    }


    public Item getById(Long id) {
        return this.itemRepository.getById(id);
    }


    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        this.itemRepository.findAll().forEach(list::add);
        return list;
    }
}
