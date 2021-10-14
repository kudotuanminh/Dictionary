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

    /**
     * Getter function to get the position in data of given wordTarget.
     *
     * @param wordTarget The wordTarget to search for.
     * @return Position of given wordTarget.
     */
    public int getIndex(String wordTarget) {
        for (int i = 0; i < words.size(); i++) {
            if (wordTarget.equals(this.getWord(i).getWordTarget())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Getter function to get the explaination of given wordTarget.
     *
     * @param wordTarget The wordTarget to search for.
     * @return Meaning of given wordTarget.
     */
    public String getWordExplain(String wordTarget) {
        int wordPos = this.getIndex(wordTarget);
        return ((wordPos == -1) ? null : this.getWord(wordPos).getWordExplain());
    }

    /**
     * Function to remove the word at given index position.
     *
     * @param index Position of word to remove.
     */
    public void removeWord(int index) {
        this.words.remove(index);
    }
}
