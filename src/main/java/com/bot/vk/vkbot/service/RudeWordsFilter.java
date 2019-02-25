package com.bot.vk.vkbot.service;

import com.bot.vk.vkbot.exceptions.RudeWordException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.bot.vk.vkbot.exceptions.RudeWordException;

@Log4j2
@Component
public class RudeWordsFilter {

    String[] rudeWords = null;

    public RudeWordsFilter() {
        try {
            String str;
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/rudeWords.txt"));
            while ((str = br.readLine()) != null) {
                rudeWords = str.toLowerCase().split("[,;:.!?\\s]+");
            }
            br.close();

        } catch (IOException ex) {
            log.error("Ошибка: {}" , ex);
        }
    }

    public void assertSentenceIsPolite(String sentence) throws RudeWordException {
        String[] words = sentence.toLowerCase().split("[,;:.!?\\s]+");

        for (String word : words) {
            for (String rudeWord : rudeWords
            ) {
                if (word.equals(rudeWord))
                    throw new RudeWordException();
            }
        }
    }
}
