package com.bot.vk.vkbot.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.bot.vk.vkbot.Exceptions.RudeWordException;

public class Filter {
    String[] rudeWords = null;

    public Filter() {
        try {
            String str = null;
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/rudeWords.txt"));
            while ((str = br.readLine()) != null) {
                rudeWords = str.toLowerCase().split("[,;:.!?\\s]+");
            }
            br.close();

        } catch (IOException ex) {
            System.out.println("Ошибка: " + ex);
        }
    }

    protected void check(String sentence) throws RudeWordException {
        String[] words = sentence.toLowerCase().split("[,;:.!?\\s]+");

        for (String word : words
        ) {
            for (String rudeWord : rudeWords
            ) {
                if (word.equals(rudeWord))
                    throw new RudeWordException();
            }
        }
    }
}
