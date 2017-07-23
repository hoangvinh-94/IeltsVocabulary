package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Healer on 02-Jun-17.
 */

public class VocabularyController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;
    Context context;

    public VocabularyController(Context context) {
        this.context = context;
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    public int numberOfUnit(int id) {
        int count = 0;
        String query = "select vocabularyID,word,phonetic,unitId,image from VOCABULARYS where  VOCABULARYS.unitId = '" + id + "' ";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            count++;
            c.moveToNext();
        }
        c.close();
        return count;
    }

    public ArrayList<Vocabulary> loadDataByUnitId(int id) {
        ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
        String query = "select vocabularyID,word,phonetic,unitId,image from VOCABULARYS where  VOCABULARYS.unitId = '" + id + "' ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Log.d("word",c.getString(1).toString().trim());
            Log.d("word",c.getString(1).toString().trim());
            vocabularies.add(new Vocabulary(c.getInt(0), c.getString(1).toString().trim(), c.getString(2).toString().trim(), c.getInt(3), c.getString(4).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return vocabularies;
    }
}
