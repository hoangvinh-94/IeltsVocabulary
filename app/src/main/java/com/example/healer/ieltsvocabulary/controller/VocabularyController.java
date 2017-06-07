package com.example.healer.ieltsvocabulary.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.healer.ieltsvocabulary.data.IeltsProvider;
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


    public VocabularyController(){

    }

    public static int loadNumOfWord(Context context, int id){
        Log.d("id",String.valueOf(id));
        int a = 0;
        Cursor c = context.getContentResolver().query(VocabularyBuiltUri.UnitEntry.CONTENT_URI,null, VocabularyBuiltUri.UnitEntry.COLUMN_ID +  "=" + id,null,null);
        c.moveToFirst();
        a = c.getInt(3);
        c.close();
        return a;
    }

    public static ArrayList<Vocabulary> loadDataByUnitId(Context context,int id){
        ArrayList<Vocabulary>  vocabularies = new ArrayList<Vocabulary>();
        Uri avatarUri = VocabularyBuiltUri.VocabularyEntry.CONTENT_URI;
        Cursor c = context.getContentResolver().query(avatarUri,null,VocabularyBuiltUri.VocabularyEntry.COLUMN_UNIT_ID + "=" + id,null,null);
        c.moveToFirst();
        while(c.isAfterLast() == false){
            vocabularies.add(new Vocabulary(c.getString(1).toString().trim(),c.getBlob(2),c.getBlob(3)));
            c.moveToNext();
        }
        c.close();
        return vocabularies;

    }
    public static byte[] convertBlob(Blob blob) {
        if(blob==null)return null;
        try {
            InputStream in = blob.getBinaryStream();
            int len = (int) blob.length(); //read as long
            long pos = 1; //indexing starts from 1
            byte[] bytes = blob.getBytes(pos, len);
            in.close();
            return bytes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

}
