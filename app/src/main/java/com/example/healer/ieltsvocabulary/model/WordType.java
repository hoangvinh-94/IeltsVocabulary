package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 22-Jun-17.
 */

public class WordType {
    private int id;
    private String type;
    private String signature;

    public WordType(int id, String type, String signature) {
        this.id = id;
        this.type = type;
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
