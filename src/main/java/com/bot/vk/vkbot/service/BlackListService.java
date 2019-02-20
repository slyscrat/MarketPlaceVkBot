package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.BlackList;
import com.bot.vk.vkbot.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlackListService {
    private BlackListRepository blackListRepository;

    @Autowired
    public BlackListService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }


    public void create(BlackList blackList) {
        this.blackListRepository.save(blackList);
    }


    public void deleteById(Long id) {
        this.blackListRepository.deleteById(id);
    }


    public BlackList getById(Long id) {
        return this.blackListRepository.getById(id);
    }


    public List<BlackList> getAll() {
        List<BlackList> list = new ArrayList<>();
        this.blackListRepository.findAll().forEach(list::add);
        return list;
    }


    public void clear() {
        this.blackListRepository.deleteAll();
    }

}
