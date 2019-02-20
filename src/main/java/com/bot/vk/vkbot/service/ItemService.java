package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot._entity.Item;
import com.bot.vk.vkbot.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemService {

    private final ItemRepository itemRepository;

    public void create(Item item) {
        itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public void clear() {
        itemRepository.deleteAll();
    }

    public Item getById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "itemId"));
    }

    public List<Item> getByUserId(Long userId) {
        return itemRepository.findByUserId(userId);
    }

    public List<Item> getByType(Long type) {
        return itemRepository.findByType(type);
    }


    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
