package com.ntm.dictionary;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList<Word>();

    public void addWord(Word word) {
        words.add(word);
    }

    public Word getWord(int index) {
        return words.get(index);
    }

    public int getSize() {
        return words.size();
    }
}
