package com.example.healer.ieltsvocabulary.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;



import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Healer on 19-Jun-17.
 */

public class ImageController {

    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase db;
    Context context;

    public ImageController(Context context){
        this.context = context;
        mOpenHelper = new LoadDataBaseSQLiteHelper(context);
        mOpenHelper.open();
        db = mOpenHelper.getMyDatabase();

    }

    public void saveImage(String f, int id){
        Bitmap img = decodeFile(f);
        byte[] data = getBitmapAsByteArray(img);
        String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
        Log.d("data",encodedImage);
        //ContentValues values = new ContentValues();
        //values.put("image",data);
        //db.update("VOCABULARYS", values,"VOCABULARYS.unitId = '"+id+"'", null);
    }

    public Bitmap loadImage(byte[] image){
        byte[] image1 = Base64.decode(image, Base64.DEFAULT);
        Bitmap imgBit = null;
        imgBit = BitmapFactory.decodeByteArray(image,0,image.length);
        return  imgBit;
    }

    public Bitmap loadImg(){
       String sql = "select VOCABULARYS.image\n" +
               "from VOCABULARYS\n" +
               "WHERE VOCABULARYS.vocabularyID = 1";
        Cursor c = db.rawQuery(sql,null);

        c.moveToFirst();
        String encode = c.getBlob(0).toString();
        byte[] decodedString = Base64.decode(encode, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
        return outputStream.toByteArray();
    }
    private Bitmap decodeFile(String f){
        AssetManager mngr = context.getAssets();
        InputStream is = null;
        try {
            is = mngr.open(f);
        } catch (IOException e) {
            e.printStackTrace();
        }	// Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is,null,o);

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize=2;
        return BitmapFactory.decodeStream(is, null, o2);
    }
}
