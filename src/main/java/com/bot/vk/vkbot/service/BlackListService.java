package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot._entity.BlackList;
import com.bot.vk.vkbot.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public void create(BlackList blackList) {
        blackListRepository.save(blackList);
    }

    public void deleteById(Long id) {
        blackListRepository.deleteById(id);
    }

    public BlackList getById(Long id) {
        return blackListRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "blacl list id"));
    }

    public List<BlackList> getAll() {
        return blackListRepository.findAll();
    }

    public void clear() {
        blackListRepository.deleteAll();
    }
}
