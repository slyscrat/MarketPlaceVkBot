package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>{
}
