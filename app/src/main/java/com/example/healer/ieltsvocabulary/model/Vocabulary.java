package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 23-May-17.
 */

public class Vocabulary {
    private int id;
    private String word;
    private String sound;

    public Vocabulary( String word) {
        this.word = word;
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

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }
}
