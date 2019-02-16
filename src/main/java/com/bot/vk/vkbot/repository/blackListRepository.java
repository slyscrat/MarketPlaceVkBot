package com.bot.vk.vkbot.repository;

import com.bot.vk.vkbot.Entity.blackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface blackListRepository extends JpaRepository<blackList, Integer>{
}
