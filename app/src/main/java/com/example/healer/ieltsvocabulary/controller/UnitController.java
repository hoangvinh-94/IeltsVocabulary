package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.healer.ieltsvocabulary.data.VocabularyBuiltUri;
import com.example.healer.ieltsvocabulary.model.Unit;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

/**
 * Created by Healer on 02-Jun-17.
 */

public class UnitController {

    public UnitController(){

    }

    public static ArrayList<Unit> loadData(Context context){
        ArrayList<Unit>  units = new ArrayList<Unit>();
        Uri avatarUri = VocabularyBuiltUri.UnitEntry.CONTENT_URI;
        Cursor c = context.getContentResolver().query(avatarUri,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            units.add(new Unit(c.getInt(0),c.getString(1).toString().trim(),Integer.parseInt(c.getString(2)),c.getString(3).trim().toString()));
            c.moveToNext();
        }
        c.close();
        return units;

    }
}
