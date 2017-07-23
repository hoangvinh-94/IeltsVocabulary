package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 22-Jun-17.
 */

public class ContextVocabulary {

    private int id;
    private String name;

    public ContextVocabulary(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
