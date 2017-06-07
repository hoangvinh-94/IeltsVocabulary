package com.example.healer.ieltsvocabulary.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Healer on 23-May-17.
 */

public class Vocabulary implements Serializable {
    private int id;
    private String word;
    private String sound;
    private byte[] phonetic;
    private byte[] detail;

    public Vocabulary(String word, byte[] phonetic, byte[] detail) {
        this.word = word;
        this.phonetic = phonetic;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public byte[] getPhonetic(){
        return phonetic;
    }
    public byte[] getDetail(){
        return detail;
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
