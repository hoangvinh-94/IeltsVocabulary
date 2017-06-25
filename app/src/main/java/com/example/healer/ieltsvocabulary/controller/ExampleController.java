package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Example;

import java.util.ArrayList;

/**
 * Created by Healer on 22-Jun-17.
 */

public class ExampleController {

    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public ExampleController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public ArrayList<Example> getExampleById(int idVocabulary){
        ArrayList<Example>  exampless = new ArrayList<Example>();
        String sql = "select exampleID,englishSentence,mean,EXAMPLES.vicID\n" +
                "from EXAMPLES, VOCABULARY_IN_CONTEXT, WORDTYPE, VOCABULARYS, VOCA_TYPE\n" +
                "where EXAMPLES.vicID = VOCABULARY_IN_CONTEXT.vicID and VOCA_TYPE.id =  VOCABULARY_IN_CONTEXT.vocaTypeID \n" +
                "and VOCA_TYPE.wordTypeID = WORDTYPE.wordTypeID and VOCABULARYS.vocabularyID = VOCA_TYPE.vocabularyID and  VOCABULARYS.vocabularyID = '"+idVocabulary+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            exampless.add(new Example(c.getInt(0),c.getString(1).toString().trim(),c.getInt(3)));
            c.moveToNext();
        }
        c.close();
        return exampless;

    }

}
