package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.entity.BlackList;
import com.bot.vk.vkbot.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BanService {

    public static final int MAX_ALLOWED_WARNINGS = 3;

    private final BlackListRepository blackListRepository;

    public Integer addWarning(Long userId) {
        BlackList bl = blackListRepository.findById(userId)                         //find by user id
                .orElseGet(() -> blackListRepository.save(new BlackList(userId)));  //or else create new one
        bl.setWarnings(bl.getWarnings() + 1);                                       //increment warnings
        blackListRepository.save(bl);
        return bl.getWarnings();
    }

    public boolean isUserBanned(Long userId) {
        return getWaqrningsCount(userId) >= MAX_ALLOWED_WARNINGS;
    }

    public Integer getWaqrningsCount(Long userId) {
        return blackListRepository.findById(userId).orElseGet(() -> blackListRepository.save(new BlackList(userId))).getWarnings();
    }
}
