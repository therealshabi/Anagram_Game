package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private ArrayList<String> user_word = new ArrayList<>();
    private HashMap<String, ArrayList> lettersToWord = new HashMap<>();
    private HashSet<String> wordSet = new HashSet<String>();
    int wordLength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            user_word.add(word);
            wordSet.add(word);
            String sorted_word = sortLetter(word);
            if (lettersToWord.containsKey(sorted_word)) {
                lettersToWord.get(sorted_word).add(word);
            } else {
                ArrayList<String> arr_list = new ArrayList<String>();
                arr_list.add(word);
                lettersToWord.put(sorted_word, arr_list);
            }
        }
    }

    //Check if the word is an Anagram

    public ArrayList<String> getAnagrams(String target) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < user_word.size(); i++) {
            String word = user_word.get(i);
            if (word.length() == target.length()) {
                String sortedLetter = sortLetter(word);
                if (sortedLetter.matches(sortLetter(target))) {
                    result.add(word);
                }
            }

        }
        return result;
    }


    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word)) {
            if (!word.contains(base))     ///Check for Substring
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (int k = 97; k <= 122; k++) {
            for (int i = 97; i <= 122; i++) {
                char a = (char) i;
                char b = (char) k;
                String result_new = word + a + b;
                String sorted_result = sortLetter(result_new);
                if (lettersToWord.containsKey(sorted_result)) {
                    for (int j = 0; j < (lettersToWord.get(sorted_result)).size(); j++) {
                        String fetched_word = (String) lettersToWord.get(sorted_result).get(j);
                        if (!word.matches(fetched_word) && !fetched_word.contains(word)) {
                            result.add(fetched_word);
                        }
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int random_index = (int) Math.random() * user_word.size();
        boolean flag = false;
        while (!flag) {
            if ((user_word.get(random_index).length() == wordLength) && getAnagramsWithOneMoreLetter(user_word.get(random_index)).size()>=MIN_NUM_ANAGRAMS) {
                flag = true;
                if (wordLength != MAX_WORD_LENGTH) {
                    wordLength += 1;
                }
                break;
            }
            random_index=(random_index+1)%user_word.size();
        }
        return user_word.get(random_index);
    }

    public String sortLetter(String letter) {
        char[] temp = letter.toCharArray();
        Arrays.sort(temp);
        String sortedLetter = new String(temp);
        return sortedLetter;
    }
}
