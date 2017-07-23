package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Example;
import com.example.healer.ieltsvocabulary.model.Practice;

import java.util.ArrayList;

/**
 * Created by Healer on 10-Jul-17.
 */

public class PracticeController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public PracticeController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }
    // get sentence from vocabularyID
    public Practice getPracticeById(int idVocabulary){
        Practice response ;
        String sql = "SELECT * FROM PRACTICES WHERE vocabularyID = '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        if(c.getCount() <= 0) {
            return null;
        }
        response = new Practice(c.getInt(0),c.getString(1).trim().toString(), c.getInt(2));
        c.close();
        return response;
    }
}
