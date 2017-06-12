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
    private String type;
    private String sound;
    private String phonetic;
    private String mean;


    public Vocabulary(String word, String phonetic,String type, String mean) {
        this.word = word;
        this.phonetic = phonetic;
        this.mean = mean;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public String getPhonetic(){
        return phonetic;
    }
    public String getMean(){
        return mean;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }

    public String getType(){
        return type;
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
