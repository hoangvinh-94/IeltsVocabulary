package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.healer.ieltsvocabulary.data.IeltsProvider;
import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.data.VocabularyBuiltUri;
import com.example.healer.ieltsvocabulary.model.Vocabulary;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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

    public int loadNumOfWord(Context context, int id) {
        Log.d("id", String.valueOf(id));
        int a = 0;
        Cursor c = context.getContentResolver().query(VocabularyBuiltUri.UnitEntry.CONTENT_URI, null, VocabularyBuiltUri.UnitEntry.COLUMN_ID + "=" + id, null, null);
        c.moveToFirst();
        a = c.getInt(3);
        c.close();
        return a;
    }


    public ArrayList<Vocabulary> loadDataByVocabularyId(int id) {
        ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
        String query = "select vocabularyID,word,phonetic,unitId,image from VOCABULARYS where  VOCABULARYS.vocabularyID = '" + id + "' ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            vocabularies.add(new Vocabulary(c.getInt(0), c.getString(1).toString().trim(), c.getString(2).toString().trim(), c.getInt(3), c.getBlob(4)));
            c.moveToNext();
        }
        c.close();
        return vocabularies;
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
            vocabularies.add(new Vocabulary(c.getInt(0), c.getString(1).toString().trim(), c.getString(2).toString().trim(), c.getInt(3), c.getBlob(4)));
            c.moveToNext();
        }
        c.close();
        return vocabularies;
    }
}
