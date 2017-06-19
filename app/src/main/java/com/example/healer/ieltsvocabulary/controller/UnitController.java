package com.example.healer.ieltsvocabulary.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.data.VocabularyBuiltUri;
import com.example.healer.ieltsvocabulary.model.Unit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public static ArrayList<Unit> loadData(Context context){
        ArrayList<Unit>  units = new ArrayList<Unit>();
        Uri avatarUri = VocabularyBuiltUri.UnitEntry.CONTENT_URI;
        Cursor c = context.getContentResolver().query(avatarUri,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            units.add(new Unit(c.getInt(0),c.getString(1).toString().trim(),c.getInt(3),c.getString(2).toString().trim()));
            c.moveToNext();
        }
        c.close();
        return units;

    }
    public void saveImageToDB(){
        ContentValues values = new ContentValues();
        values.put("picture","vinh");
        db.update("VOCABULARYS", values,"VOCABULARYS.vocabularyID = 2", null );
        /*
        Uri fileUri =  Uri.parse("file:///C:/Users/vinhp/Desktop/new-forest-accommodation-large-icon.png");
        try {
            FileInputStream fis = new FileInputStream(String.valueOf(fileUri));
            byte[] image = new byte[0];
            try {
                image = new byte[fis.available()];
                fis.read(image);
                ContentValues values = new ContentValues();
                values.put("image",image);
                db.update("VOCABULARYS", values,"VOCABULARYS.vocabularyID = ?", new String[]{"2"} );
                fis.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
*/
    }


}
