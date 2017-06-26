package com.example.healer.ieltsvocabulary.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;



import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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



    public static void export() throws IOException {
        //Open your local db as the input stream
        String inFileName = "/data/data/com.example.healer.ieltsvocabulary/databases/IeltsVocabulary.db";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String pathDir = "/Android/com.example.healer.ieltsvocabulary/";

        String outFileName = pathDir +"IeltsVocabulary_copy.db";
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        Log.d("output",output.toString());
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
    }
    public void saveImage(String f, int id){
        Bitmap img = decodeFile(f);
        //Log.d("imageEncode",encodeTobase64(img));
        String encode = encodeTobase64(img);
        //byte[] data = getBitmapAsByteArray(img);
        //String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
        Log.d("data", encode);
        ContentValues values = new ContentValues();
        values.put("image",encode);
        db.update("VOCABULARYS", values,"VOCABULARYS.vocabularyID = '"+id+"'", null);
    }

    public Bitmap loadImage(byte[] image){
        //byte[] image1 = Base64.decode(image, Base64.DEFAULT);
        Bitmap imgBit = null;
        imgBit = BitmapFactory.decodeByteArray(image,0,image.length);
        return  imgBit;
    }

    public String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public Bitmap getImage(String image_icon_data ) {
        if(image_icon_data != null) {
            byte[] image_data = Base64.decode(image_icon_data, Base64.DEFAULT);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.outHeight = 32; //32 pixles
            options.outWidth = 32; //32 pixles
            options.outMimeType = "image/*"; //this could be image/jpeg, image/png, etc

            return BitmapFactory.decodeByteArray(image_data, 0, image_data.length, options);
        }
        return null;
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
