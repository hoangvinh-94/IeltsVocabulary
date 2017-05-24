package com.example.healer.ieltsvocabulary.model;

import java.util.ArrayList;

/**
 * Created by Healer on 23-May-17.
 */

public class Lesson {
    private int lessonId;
    private String lessonName;
    private ArrayList<Vocabulary> vocabularies;

    public Lesson( int id, String word,  ArrayList<Vocabulary> vocabularies) {
        this.lessonId = id;
        this.lessonName = word;
        this.vocabularies = vocabularies;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public  ArrayList<Vocabulary>  getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies( ArrayList<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public String getNameLesson() {
        return lessonName;
    }

    public void setNameLesson(String word) {
        this.lessonName = word;
    }
}
