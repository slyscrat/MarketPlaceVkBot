package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.BlackList;
import com.bot.vk.vkbot.Exceptions.BanException;
import com.bot.vk.vkbot.repository.BlackListRepository;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import javax.persistence.Column;

@Service
public class BanService {

    int MAX_ALOWE;

    public BanService(int MAX_ALOWE){
        this.MAX_ALOWE = MAX_ALOWE;
    }


    @Autowired BlackListRepository blackListRepository;

    public void addWarning(Long userId) throws BanException{

        @Id @Column(name = "id_user")
        private Long id;
        private Integer warnings;

        BlackList bl = blackListRepository.findById(userId);
        bl.setWarnings(bl.getWarnings + 1);
        bl.blackListRepository.save(bl);

        if(bl.getWarnings() > MAX_ALOWE){
            throw new BanException();
        }
    }



}
