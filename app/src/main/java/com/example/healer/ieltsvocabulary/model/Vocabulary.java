package com.example.healer.ieltsvocabulary.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Healer on 23-May-17.
 */

public class Vocabulary implements Serializable {
    private int id;
    private int unitId;
    private String word;
    private String phonetic;
    private byte[] image;

    public Vocabulary(){

    }

    public Vocabulary(int id, String word, String phonetic, int unitId, byte[] image) {
        this.id = id;
        this.word = word;
        this.phonetic = phonetic;
        this.unitId = unitId;
        this.image = image;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
