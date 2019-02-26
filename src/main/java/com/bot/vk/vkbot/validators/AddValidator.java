package com.bot.vk.vkbot.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddValidator implements Validator{

    @Override
    public boolean isValid(String body){
        try {
            Pattern pattern = Pattern.compile("(/add) \"([^\"]{4,100})\" \"([^\"]{10,})\" ([0-9]+) ([0-9.]+)");
            Matcher matcher = pattern.matcher(body);
            return matcher.matches();
        }
        catch(Exception ex){
            return false;
        }
    }
}
