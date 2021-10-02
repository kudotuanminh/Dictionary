package com.ntm.dictionary;

import java.util.Vector;

public class Dictionary {
    public static Vector<Word> words = new Vector<Word>();


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
