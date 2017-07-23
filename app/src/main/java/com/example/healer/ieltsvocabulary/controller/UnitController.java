package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.model.Unit;

import java.util.ArrayList;

/**
 * Created by Healer on 02-Jun-17.
 */

public class UnitController {

    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;

    public UnitController(Context context){
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();
    }

    // get Data from Unit table in Sqlite DB
    public  ArrayList<Unit> loadData(){
        ArrayList<Unit>  units = new ArrayList<Unit>();
        String sql = "SELECT * FROM UNITS";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            units.add(new Unit(c.getInt(0),c.getString(1).toString().trim(),c.getInt(3),c.getString(2).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return units;

    }

    public Unit getUnit(int id){
        Unit unit  = new Unit();
        String sql = "SELECT * FROM UNITS WHERE unitID = '"+id+"'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        unit.setId(c.getInt(0));
        unit.setName(c.getString(1).toString().trim());
        unit.setNumberOfWord(c.getInt(3));
        unit.setAvatar(c.getString(2).toString().trim());
        c.close();
        return unit;
    }

}
