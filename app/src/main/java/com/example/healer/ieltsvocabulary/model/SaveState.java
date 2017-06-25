package com.example.healer.ieltsvocabulary.model;

/**
 * Created by Healer on 23-Jun-17.
 */

public class SaveState {
    private int currentNumberWord;
    private int idUnit;

    public SaveState(int currentNumberWord, int idUnit) {
        this.currentNumberWord = currentNumberWord;
        this.idUnit = idUnit;
    }

    public int getCurrentNumberWord() {
        return currentNumberWord;
    }

    public void setCurrentNumberWord(int currentNumberWord) {
        this.currentNumberWord = currentNumberWord;
    }

    public int getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(int idUnit) {
        this.idUnit = idUnit;
    }
}
