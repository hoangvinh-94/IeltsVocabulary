package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.WordForm;
import com.example.healer.ieltsvocabulary.model.WordType;

import java.util.ArrayList;

/**
 * Created by Healer on 25-Jun-17.
 */

public class WordFormController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public WordFormController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public ArrayList<WordForm> getWordFormById(int idVocabulary){
        ArrayList<WordForm>  wordForms = new ArrayList<WordForm>();
        String sql = "select WORDFORM.id, WORDFORM.word, WORDFORM.vocabularyID\n" +
                "from VOCABULARYS, WORDFORM\n" +
                "where VOCABULARYS.vocabularyID = WORDFORM.vocabularyID and VOCABULARYS.vocabularyID  = '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            wordForms.add(new WordForm(c.getInt(0),c.getString(1).toString().trim(),c.getInt(2)));
            c.moveToNext();
        }
        c.close();
        return wordForms;

    }
}
