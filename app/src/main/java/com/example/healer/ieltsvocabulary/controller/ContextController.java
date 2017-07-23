package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.ContextVocabulary;
import com.example.healer.ieltsvocabulary.model.Example;

import java.util.ArrayList;

/**
 * Created by Healer on 22-Jun-17.
 */

public class ContextController {

    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public ContextController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public ArrayList<ContextVocabulary> getContextById(int idVocabulary){
        ArrayList<ContextVocabulary>  contexts = new ArrayList<ContextVocabulary>();
        String sql = "select CONTEXTS.contextID, CONTEXTS.name, VOCA_TYPE.vocabularyID \n" +
                "FROM VOCABULARY_IN_CONTEXT, CONTEXTS, VOCA_TYPE, VOCABULARYS \n" +
                "WHERE VOCA_TYPE.id = VOCABULARY_IN_CONTEXT.vocaTypeID and VOCABULARYS.vocabularyID = VOCA_TYPE.vocabularyID and VOCABULARY_IN_CONTEXT.contextID = CONTEXTS.contextID and CONTEXTS.contextID != 4 and VOCABULARYS.vocabularyID =  '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        while(c.isAfterLast() == false){
            contexts.add(new ContextVocabulary(c.getInt(0), c.getString(1).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return contexts;

    }

}
