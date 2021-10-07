package com.ntm.dictionary;

import java.util.ArrayList;

/** Held logics for a Dictionary. */
public class Dictionary {
    /** @param words The ArrayList to stores the word list of the Dictionary. */
    protected ArrayList<Word> words = new ArrayList<Word>();

    /**
     * Adds a word to the Dictionary's data.
     *
     * @param word The new word that are being added to the ArrayList words.
     */
    public void addWord(Word word) {
        words.add(word);
    }

    /**
     * Getter function to get the word at the index position of the Dictionary's
     * data.
     *
     * @param index The position of the word to get.
     * @return The word at the index position of the ArrayList words.
     */
    public Word getWord(int index) {
        return words.get(index);
    }

    /**
     * Getter function to get the size of the Dictionary's data.
     *
     * @return words's ArrayList length.
     */
    public int getSize() {
        return words.size();
    }

    public String getWordExplain(String wordTarget) {
        for (int i = 0; i < words.size(); i++) {
            if (wordTarget.equals(getWord(i).getWordTarget())) {
                return getWord(i).getWordExplain();
            }
        }
        return null;
    }
}
