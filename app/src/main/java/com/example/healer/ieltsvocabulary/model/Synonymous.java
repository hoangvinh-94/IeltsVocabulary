package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 22-Jun-17.
 */

public class Synonymous {
    private int id;
    private String word;
    private int vocabularyId;

    public Synonymous(int id, String word, int vocabularyId) {
        this.id = id;
        this.word = word;
        this.vocabularyId = vocabularyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getVocabularyId() {
        return vocabularyId;
    }

    public void setVocabularyId(int vocabularyId) {
        this.vocabularyId = vocabularyId;
    }
}
