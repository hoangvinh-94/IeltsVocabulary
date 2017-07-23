package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 10-Jul-17.
 */

public class Practice {
    private int id;
    private String sentence;
    private int vocabularyID;

    public Practice(int id, String sentence, int vocabularyID) {
        this.id = id;
        this.sentence = sentence;
        this.vocabularyID = vocabularyID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getVocabularyID() {
        return vocabularyID;
    }

    public void setVocabularyID(int vocabularyID) {
        this.vocabularyID = vocabularyID;
    }
}
