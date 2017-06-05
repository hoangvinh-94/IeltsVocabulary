package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.healer.ieltsvocabulary.data.IeltsProvider;
import com.example.healer.ieltsvocabulary.data.VocabularyBuiltUri;
import com.example.healer.ieltsvocabulary.model.Vocabulary;
import android.os.Bundle;
import java.util.ArrayList;


/**
 * Created by Healer on 02-Jun-17.
 */

public class VocabularyController {


    public VocabularyController(){

    }

    public static int loadNumOfWord(Context context, int id){
        int a = 0;
        Cursor c = context.getContentResolver().query(VocabularyBuiltUri.UnitEntry.CONTENT_URI,null, VocabularyBuiltUri.UnitEntry.COLUMN_ID +  "=" + id,null,null);
        c.moveToFirst();
        a = c.getInt(2);
        c.close();
        return a;
    }

    public static ArrayList<Vocabulary> loadDataByUnitId(Context context,int id){
        ArrayList<Vocabulary>  vocabularies = new ArrayList<Vocabulary>();
        Uri avatarUri = VocabularyBuiltUri.VocabularyEntry.CONTENT_URI;
        Cursor c = context.getContentResolver().query(avatarUri,null,VocabularyBuiltUri.VocabularyEntry.COLUMN_UNIT_ID + "=" + id,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            vocabularies.add(new Vocabulary(c.getString(1).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return vocabularies;

    }

}
