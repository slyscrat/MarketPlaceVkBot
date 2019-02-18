package com.bot.vk.vkbot.service.impl;

import com.bot.vk.vkbot.Entity.Item;
import com.bot.vk.vkbot.repository.ItemRepository;
import com.bot.vk.vkbot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item addItem(Item item){
        return itemRepository.save(item);
    }

    @Override
    public void delete(long id){
        itemRepository.deleteById(id);
    }

    @Override
    public Item getById(long id){
        return new Item();
    }

    @Override
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        itemRepository.findAll().forEach(list::add);
        return list;
    }
}
