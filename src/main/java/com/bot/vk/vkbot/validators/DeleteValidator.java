package com.bot.vk.vkbot.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DeleteValidator implements Validator{

    @Override
    public boolean isValid(String body){
        try {
            Pattern pattern = Pattern.compile("(/delete) ([0-9]+)");
            Matcher matcher = pattern.matcher(body);
            return matcher.matches();
        }
        catch(Exception ex){
            return false;
        }
    }
}
