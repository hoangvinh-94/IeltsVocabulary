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

    public VocabularyController(Context context) {
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

    public ArrayList<Vocabulary> loadDataByUnitId(int id) {
        ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
        String query = "select distinct VOCABULARYS.vocabularyID, VOCABULARYS.word, VOCABULARYS.phonetic, WORDTYPE.type, WORDTYPE.signature, WORDTYPE.wordTypeID, MEANS.mean \n" +
                "from MEANS,  VOCABULARY_IN_CONTEXT, WORDTYPE, VOCABULARYS, (select  * from VOCA_TYPE group by VOCA_TYPE.vocabularyID) as A\n" +
                "where MEANS.vicID = VOCABULARY_IN_CONTEXT.vicID and A.id =  VOCABULARY_IN_CONTEXT.vocaTypeID\n" +
                "and A.wordTypeID = WORDTYPE.wordTypeID and VOCABULARYS.vocabularyID = A.vocabularyID and  VOCABULARYS.unitId = '" + id + "' ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            vocabularies.add(new Vocabulary(c.getInt(0), c.getString(1).toString().trim(), c.getString(2).toString().trim(), c.getString(3).toString().trim(), c.getString(4).toString().trim(), c.getInt(5),c.getString(6).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return vocabularies;
    }

    public String getSynonym(int id) {
        String str = "";

        String query = "  select SYNONYMOUS.word\n" +
                "        from VOCABULARYS, SYNONYMOUS\n" +
                "        where VOCABULARYS.vocabularyID = SYNONYMOUS.vocabularyID and VOCABULARYS.vocabularyID = '" + id + "' ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            str = c.getString(0);
            c.moveToNext();
        }
        c.close();
        return str;
    }

    public ArrayList<String> getExample(int id) {
        ArrayList<String> examples = new ArrayList<String>();

        String query = "  select EXAMPLES.englishSentence \n" +
                "                from MEANS,  VOCABULARY_IN_CONTEXT, VOCA_TYPE, WORDTYPE, VOCABULARYS, EXAMPLES\n" +
                "                where MEANS.vicID = VOCABULARY_IN_CONTEXT.vicID and VOCA_TYPE.id =  VOCABULARY_IN_CONTEXT.vocaTypeID\n" +
                "                and VOCA_TYPE.wordTypeID = WORDTYPE.wordTypeID and VOCABULARYS.vocabularyID = VOCA_TYPE.vocabularyID\n" +
                "and VOCABULARY_IN_CONTEXT.vicID = EXAMPLES.vicID and  VOCABULARYS.vocabularyID = '" + id + "' ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            examples.add(c.getString(0));
            c.moveToNext();
        }
        c.close();
        return examples;
    }
    public ArrayList<Vocabulary> getWordType(int id, int wordTypeID) {
        ArrayList<Vocabulary> wordTypes = new ArrayList<Vocabulary>();

        String query = " /*select *\n" +
                "from (select distinct (VOCA_TYPE.vocabularyID, VOCA_TYPE.id)\n" +
                "from VOCA_TYPE) as A, VOCABULARY_IN_CONTEXT, MEANS\n" +
                "where VOCABULARY_IN_CONTEXT.*/\n" +
                "select distinct VOCABULARYS.vocabularyID, VOCABULARYS.word, VOCABULARYS.phonetic, WORDTYPE.type, WORDTYPE.signature, VOCA_TYPE.wordTypeID, MEANS.mean\n" +
                "\n" +
                "from MEANS,  VOCABULARY_IN_CONTEXT, WORDTYPE, VOCABULARYS, VOCA_TYPE\n" +
                "where MEANS.vicID = VOCABULARY_IN_CONTEXT.vicID and VOCA_TYPE.id =  VOCABULARY_IN_CONTEXT.vocaTypeID\n" +
                "and VOCA_TYPE.wordTypeID = WORDTYPE.wordTypeID and VOCABULARYS.vocabularyID = VOCA_TYPE.vocabularyID and  VOCABULARYS.vocabularyID = '" + id + "' and VOCA_TYPE.wordTypeID != '" + wordTypeID + "'\n  ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            wordTypes.add(new Vocabulary(c.getInt(0), c.getString(1).toString().trim(), c.getString(2).toString().trim(), c.getString(3).toString().trim(), c.getString(4).toString().trim(), c.getInt(5),c.getString(6).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return wordTypes;
    }

    public String getUnsynonym(int id) {
        String str = "";

        String query = "  select UNSYNONYMOUS.word\n" +
                "        from VOCABULARYS, UNSYNONYMOUS\n" +
                "        where VOCABULARYS.vocabularyID = UNSYNONYMOUS.vocabularyID and VOCABULARYS.vocabularyID = '" + id + "' ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            str = c.getString(0);
            c.moveToNext();
        }
        c.close();
        return str;
    }
}