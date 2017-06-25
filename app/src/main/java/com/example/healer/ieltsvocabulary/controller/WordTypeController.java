package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.WordType;

import java.util.ArrayList;

/**
 * Created by Healer on 22-Jun-17.
 */

public class WordTypeController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;
    Context context;

    public WordTypeController(Context context) {
        this.context = context;
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    public ArrayList<WordType> getTypeByVocabularyId(int id){
        ArrayList<WordType>  types = new ArrayList<WordType>();
        String sql = "select WORDTYPE.wordTypeID, type, signature\n" +
                "from VOCABULARYS, VOCA_TYPE, WORDTYPE\n" +
                "where VOCABULARYS.vocabularyID = VOCA_TYPE.vocabularyID and VOCA_TYPE.wordTypeID = WORDTYPE.wordTypeID and VOCABULARYS.vocabularyID = '"+id+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            types.add(new WordType(c.getInt(0),c.getString(1).toString().trim(),c.getString(2).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return types;
    }
}
