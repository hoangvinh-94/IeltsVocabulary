package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 22-Jun-17.
 */

public class Example {
    private int id;
    private String engSentence;
    private String vietSentence;
    private int voca_in_context;

    public Example(int id, String engSentence, int voca_in_context) {
        this.id = id;
        this.engSentence = engSentence;
        this.voca_in_context = voca_in_context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngSentence() {
        return engSentence;
    }

    public void setEngSentence(String engSentence) {
        this.engSentence = engSentence;
    }

    public String getVietSentence() {
        return vietSentence;
    }

    public void setVietSentence(String vietSentence) {
        this.vietSentence = vietSentence;
    }

    public int getVoca_in_context() {
        return voca_in_context;
    }

    public void setVoca_in_context(int voca_in_context) {
        this.voca_in_context = voca_in_context;
    }
}
