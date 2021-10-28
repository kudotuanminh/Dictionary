package com.ntm.dictionary;

import java.util.Objects;

/** Held logics for a pair of target word and its explanation. */
public class Word {
    /** @param wordTarget The current word itself. */
    private String wordTarget = "";
    /** @param wordExplain The meaning of the current word. */
    private String wordExplain = "";
    /** @param wordExplain The pronunciation of the current word. */
    private String wordPronounce = "";

    /**
     * Constructor function that takes 2 string variables.
     *
     * @param wordTarget The target word that's being used to construct the
     *        word.
     * @param wordExplain The explanation that's being used to construct the
     *        word.
     */
    Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    /**
     * Constructor function that takes 2 string variables.
     *
     * @param wordTarget The target word that's being used to construct the
     *        word.
     * @param wordExplain The explanation that's being used to construct the
     *        word.
     * @param wordPronounce The pronunciation that's being used to construct the
     *        word.
     */
    Word(String wordTarget, String wordExplain, String wordPronounce) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.wordPronounce = wordPronounce;
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
     * Getter function to get current word's wordPronounce.
     *
     * @return Current word's wordPronounce.
     */
    public String getWordPronounce() {
        return this.wordPronounce;
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

    /**
     * Setter function to set current word's wordPronounce.
     *
     * @param wordPronounce New explanation to set.
     */
    public void setWordPronounce(String wordPronounce) {
        this.wordPronounce = wordPronounce;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Word)) {
            return false;
        }
        Word word = (Word) obj;
        return Objects.equals(wordTarget, word.wordTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordTarget);
    }

}
