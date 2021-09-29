package com.ntm.dictionary;

/** Held logics for a pair of target word and its explanation. */
public class Word {
    /** @param wordTarget The current word itself. */
    private String wordTarget = "";
    /** @param wordExplain The explanation of the current word. */
    private String wordExplain = "";

    /**
     * Constructor function that takes 2 string variables.
     *
     * @param wordTarget  The target word that's being used to construct the word.
     * @param wordExplain The explanation that's being used to construct the word.
     */
    Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    /**
     * Getter function to get current word's wordTarget.
     *
     * @return Current word's wordTarget.
     */
    public String getWordTarget() {
        return this.wordTarget;
    }

    /**
     * Getter function to get current word's wordExplain.
     *
     * @return Current word's wordExplain.
     */
    public String getWordExplain() {
        return this.wordExplain;
    }

    /**
     * Setter function to set current word's wordTarget.
     *
     * @param wordTarget New target word to set.
     */
    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    /**
     * Setter function to set current word's wordExplain.
     *
     * @param wordExplain New explanation to set.
     */
    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
