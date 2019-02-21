package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.entity.Type;
import com.bot.vk.vkbot.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TypeService {
    private TypeRepository typeRepository;

    public void create(Type type) {
        this.typeRepository.save(type);
    }

    public void delete(Type type) {
        this.typeRepository.delete(type);
    }

    public Type getById(Long id) {
        return this.typeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "messageId"));
    }

    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    public void clear() {
        this.typeRepository.deleteAll();
    }
}
