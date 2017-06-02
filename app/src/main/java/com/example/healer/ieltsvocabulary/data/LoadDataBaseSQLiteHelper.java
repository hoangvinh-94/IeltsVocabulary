package com.example.healer.ieltsvocabulary.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class LoadDataBaseSQLiteHelper extends SQLiteOpenHelper {

	private String DB_PATH = "com.example.healer.ieltsvocabulary";
	//"/data/data/com.example.learnvocabulary/databases/";
	private static String DB_NAME = "cambrige_vocabulary.db";

	private Context context;
	private SQLiteDatabase myDatabase;
	public LoadDataBaseSQLiteHelper(Context context){
		super(context, DB_NAME, null, 1);

		this.context = context;
		this.DB_PATH = this.context.getDatabasePath(DB_NAME).getAbsolutePath();
		boolean dbexist = checkdatabase();

		if(dbexist)
		{
			System.out.println("Database loading!");
		}
		else
		{
			System.out.println("Database doesn't exist!");

			createDatabse();
		}

	}
	public SQLiteDatabase getMyDatabase()
	{
		return myDatabase;
	}

	private void createDatabse() {
		// TODO Auto-generated method stub
		this.getReadableDatabase();

		try
		{
			copyDatabase();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void copyDatabase() throws IOException {

		AssetManager dirPath = context.getAssets();

		InputStream myinput = context.getAssets().open(DB_NAME);

		String outFileName = DB_PATH + DB_NAME;

		OutputStream myOutput = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int length;

		while ((length = myinput.read(buffer)) > 0)
		{
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myinput.close();
	}

	public void open()
	{
		String myPath = DB_PATH + DB_NAME;
		myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	}

	public synchronized void close()
	{
		myDatabase.close();
		super.close();
	}


	// Check existing database
	private boolean checkdatabase() {

		boolean checkdb = false;

		try
		{
			String myPath = DB_PATH + DB_NAME;
			File dbfile = new File(myPath);
			checkdb = dbfile.exists();
		}
		catch (SQLiteException e)
		{
			System.out.println("Databse doesn't exist!");
		}

		return checkdb;
	}

	public LoadDataBaseSQLiteHelper(Context context, String name,
                                    CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(newVersion > oldVersion){
			try {
				copyDatabase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
