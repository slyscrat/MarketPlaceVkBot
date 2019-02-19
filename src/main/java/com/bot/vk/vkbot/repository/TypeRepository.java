package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Type;
import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<Type, Long> {
    Type getById(Long id);
    void deleteById(Long id);
}
