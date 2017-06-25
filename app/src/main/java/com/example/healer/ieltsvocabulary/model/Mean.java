package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 22-Jun-17.
 */

public class Mean {
    private int id;
    private String mean;
    private int voca_in_context;

    public Mean(int id, String mean, int voca_in_context) {
        this.id = id;
        this.mean = mean;
        this.voca_in_context = voca_in_context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getVoca_in_context() {
        return voca_in_context;
    }

    public void setVoca_in_context(int voca_in_context) {
        this.voca_in_context = voca_in_context;
    }
}
