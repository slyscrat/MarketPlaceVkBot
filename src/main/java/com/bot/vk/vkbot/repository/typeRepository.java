package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface typeRepository extends JpaRepository<type, Integer>{
}
