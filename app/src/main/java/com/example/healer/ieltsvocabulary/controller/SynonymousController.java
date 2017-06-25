package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Synonymous;

import java.util.ArrayList;

/**
 * Created by Healer on 22-Jun-17.
 */

public class SynonymousController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public SynonymousController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public Synonymous getSynonymousById(int idVocabulary){
        Synonymous synonymous;
        String sql = "select synonymousID,SYNONYMOUS.word,SYNONYMOUS.vocabularyID\n" +
                "from VOCABULARYS,SYNONYMOUS\n" +
                "where VOCABULARYS.vocabularyID = SYNONYMOUS.vocabularyID and VOCABULARYS.vocabularyID  =  '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        synonymous = new Synonymous(c.getInt(0),c.getString(1).toString().trim(),c.getInt(2));
        c.close();
        return synonymous;

    }
}
