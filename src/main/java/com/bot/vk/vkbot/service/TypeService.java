package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.Entity.Type;
import com.bot.vk.vkbot.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {
    private TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    public void create(Type type) {
        this.typeRepository.save(type);
    }


    public void delete(Type type) {
        this.typeRepository.delete(type);
    }


    public Type getById(Long id) {
        return this.typeRepository.getById(id);
    }


    public List<Type> getAll() {
        List<Type> list = new ArrayList<>();
        this.typeRepository.findAll().forEach(list::add);
        return list;
    }


    public void clear() {
        this.typeRepository.deleteAll();
    }
}
