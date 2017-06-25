package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Unsynonymous;

import java.util.ArrayList;

/**
 * Created by Healer on 22-Jun-17.
 */

public class UnsynonymousController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public UnsynonymousController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public Unsynonymous getUnsynonymousById(int idVocabulary){
        Unsynonymous  unsynonymous = null;
        String sql = "select unsynonymousID,UNSYNONYMOUS.word,UNSYNONYMOUS.vocabularyID\n" +
                "from VOCABULARYS,UNSYNONYMOUS\n" +
                "where VOCABULARYS.vocabularyID = UNSYNONYMOUS.vocabularyID and VOCABULARYS.vocabularyID  =  '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        if(c.getCount() <= 0) {
            return null;
        }
        unsynonymous = new Unsynonymous(c.getInt(0), c.getString(1).toString().trim(), c.getInt(2));
        c.close();
        return unsynonymous;
    }
}
