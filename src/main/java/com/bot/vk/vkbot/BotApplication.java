package com.bot.vk.vkbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
public class BotApplication {

    public static void main(String[] args) throws SQLException{
        SpringApplication.run(BotApplication.class, args);
    }
}