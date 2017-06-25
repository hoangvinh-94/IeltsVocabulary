package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Unit;

import java.util.ArrayList;

/**
 * Created by Healer on 22-Jun-17.
 */

public class MeanController {
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public MeanController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public ArrayList<Mean> getMeanById(int idVocabulary){
        ArrayList<Mean>  means = new ArrayList<Mean>();
        String sql = "select meanID, mean, MEANS.vicID\n" +
                "from MEANS,  VOCABULARY_IN_CONTEXT, WORDTYPE, VOCABULARYS, VOCA_TYPE\n" +
                "where MEANS.vicID = VOCABULARY_IN_CONTEXT.vicID and VOCA_TYPE.id =  VOCABULARY_IN_CONTEXT.vocaTypeID \n" +
                "and VOCA_TYPE.wordTypeID = WORDTYPE.wordTypeID and VOCABULARYS.vocabularyID = VOCA_TYPE.vocabularyID and  VOCABULARYS.vocabularyID = '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            means.add(new Mean(c.getInt(0),c.getString(1).toString().trim(),c.getInt(2)));
            c.moveToNext();
        }
        c.close();
        return means;

    }
}
