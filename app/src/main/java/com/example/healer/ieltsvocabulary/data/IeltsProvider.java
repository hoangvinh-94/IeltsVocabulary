/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.healer.ieltsvocabulary.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class IeltsProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private LoadDataBaseSQLiteHelper mOpenHelper;
    private SQLiteDatabase myDatabase;
    static final int UNIT = 100;
    static final int UNIT_ID = 105;
    static final int VOCABULARY = 101;
    static final int VOCABULARY_AUDIO = 102;
   // static final int VOCABULARY_AUDIO = 103;

    private static final SQLiteQueryBuilder sVocabuaryInUnitQueryBuilder;

    static{
        sVocabuaryInUnitQueryBuilder = new SQLiteQueryBuilder();
        
        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sVocabuaryInUnitQueryBuilder.setTables(
                VocabularyBuiltUri.UnitEntry.TABLE_NAME + " INNER JOIN " +
                        VocabularyBuiltUri.VocabularyEntry.TABLE_NAME +
                        " ON " + VocabularyBuiltUri.UnitEntry.TABLE_NAME +
                        "." + VocabularyBuiltUri.UnitEntry.TABLE_ID +
                        " = " + VocabularyBuiltUri.VocabularyEntry.TABLE_NAME +
                        "." + VocabularyBuiltUri.VocabularyEntry.COLUMN_UNIT_ID);
    }

    //What Unit?
    private static final String sUnitSelection =
            VocabularyBuiltUri.UnitEntry.TABLE_NAME+
                    "." + VocabularyBuiltUri.UnitEntry.TABLE_ID + " = ? ";
    //vocabulary.audioUri = ?
    private static final String sVoccabularyAudio =
            VocabularyBuiltUri.VocabularyEntry.TABLE_NAME+
                    "." + VocabularyBuiltUri.VocabularyEntry.COLUMN_AUDIO_URI + " = ? ";

//    //location.location_setting = ? AND date >= ?
//    private static final String sLocationSettingWithStartDateSelection =
//            VocabularyBuiltUri.LocationEntry.TABLE_NAME+
//                    "." + VocabularyBuiltUri.LocationEntry.COLUMN_LOCATION_SETTING + " = ? AND " +
//                    VocabularyBuiltUri.WeatherEntry.COLUMN_DATE + " >= ? ";
//
//    //location.location_setting = ? AND date = ?
//    private static final String sLocationSettingAndDaySelection =
//            VocabularyBuiltUri.LocationEntry.TABLE_NAME +
//                    "." + VocabularyBuiltUri.LocationEntry.COLUMN_LOCATION_SETTING + " = ? AND " +
//                    VocabularyBuiltUri.WeatherEntry.COLUMN_DATE + " = ? ";

    private Cursor getVocabularyByUnitId(Uri uri, String[] projection, String sortOrder) {
        //String unitId = VocabularyBuiltUri.UnitEntry.getUnitIdFromUri(uri);
        //long startDate = VocabularyBuiltUri.WeatherEntry.getStartDateFromUri(uri);

        String[] selectionArgs = null;
        String selection = sUnitSelection;

//        if (startDate == 0) {
//            selection = sLocationSettingSelection;
//            selectionArgs = new String[]{locationSetting};
//        } else {
//            selectionArgs = new String[]{locationSetting, Long.toString(startDate)};
//            selection = sLocationSettingWithStartDateSelection;
//        }

        return sVocabuaryInUnitQueryBuilder.query(myDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getVocabularyAudiUri(
            Uri uri, String[] projection, String sortOrder) {
        String locationSetting = VocabularyBuiltUri.VocabularyEntry.getAudioFromUri(uri);

        return sVocabuaryInUnitQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sVoccabularyAudio,
                new String[]{locationSetting},
                null,
                null,
                sortOrder
        );
     }

//    private Cursor getWeatherByLocationSettingAndDate(
//            Uri uri, String[] projection, String sortOrder) {
//        String locationSetting = VocabularyBuiltUri.WeatherEntry.getLocationSettingFromUri(uri);
//        long date = VocabularyBuiltUri.WeatherEntry.getDateFromUri(uri);
//
//        return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
//                projection,
//                sLocationSettingAndDaySelection,
//                new String[]{locationSetting, Long.toString(date)},
//                null,
//                null,
//                sortOrder
//        );
   // }

    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority= VocabularyBuiltUri.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // VocabularyBuiltUri to help define the types to the UriMatcher.

        uriMatcher.addURI(authority, VocabularyBuiltUri.PATH_UNIT,UNIT);
        uriMatcher.addURI(authority, VocabularyBuiltUri.PATH_VOCABULARY,VOCABULARY  );
        uriMatcher.addURI(authority, VocabularyBuiltUri.PATH_VOCABULARY + "/*" ,VOCABULARY_AUDIO);
       // uriMatcher.addURI(authority, VocabularyBuiltUri.PATH_WEATHER + "/*/#" ,WEATHER_WITH_LOCATION_AND_DATE);

        // 3) Return the new matcher!
        return uriMatcher;
    }

    /*
        Students: We've coded this for you.  We just create a new WeatherDbHelper for later use
        here.
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new LoadDataBaseSQLiteHelper(getContext());
        mOpenHelper.open();
        myDatabase = mOpenHelper.getMyDatabase();
        Log.e("onCreate Provider",mOpenHelper.getDatabaseName());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.

     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Student: Uncomment and fill out these two cases
//            case WEATHER_WITH_LOCATION_AND_DATE:
//            case WEATHER_WITH_LOCATION:
            case UNIT:
                return VocabularyBuiltUri.UnitEntry.CONTENT_TYPE;
            case VOCABULARY:
                return VocabularyBuiltUri.VocabularyEntry.CONTENT_TYPE;
            case VOCABULARY_AUDIO:
                return VocabularyBuiltUri.VocabularyEntry.CONTENT_TYPE;
//            case WEATHER_WITH_LOCATION_AND_DATE:
//                return VocabularyBuiltUri.WeatherEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
//            // "weather/*/*"
//            case WEATHER_WITH_LOCATION_AND_DATE:
//            {
//                retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
//                break;
//            }
            // "weather/*"
            case VOCABULARY_AUDIO: {
                retCursor = getVocabularyAudiUri(uri, projection, sortOrder);
                break;
            }
            // "weather"
            case UNIT: {
                retCursor = myDatabase.query(
                        VocabularyBuiltUri.UnitEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            // "location"
            case VOCABULARY: {
                retCursor = myDatabase.query(
                        VocabularyBuiltUri.VocabularyEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = myDatabase;
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case UNIT: {
               // normalizeDate(values);
                long _id = db.insert(VocabularyBuiltUri.UnitEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = VocabularyBuiltUri.UnitEntry.buildUnitUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case VOCABULARY:{
                //normalizeDate(values);
                long _id=db.insert(VocabularyBuiltUri.VocabularyEntry.TABLE_NAME, null, values);
                if(_id > 0)
                    returnUri= VocabularyBuiltUri.VocabularyEntry.buildVocabularyUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Student: Start by getting a writable database
       // final SQLiteDatabase db
        final SQLiteDatabase db= myDatabase;
        // Student: Use the uriMatcher to match the WEATHER and LOCATION URI's we are going to
        // handle.  If it doesn't match these, throw an UnsupportedOperationException.
        final int match=sUriMatcher.match(uri);
        int row;
        // Student: A null value deletes all rows.  In my implementation of this, I only notified
        // the uri listeners (using the content resolver) if the rowsDeleted != 0 or the selection
        // is null.
        // Oh, and you should notify the listeners here.
        if(selection == null) selection="1";
        switch (match){
            case UNIT:
                row=db.delete(VocabularyBuiltUri.UnitEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case VOCABULARY:
                row=db.delete(VocabularyBuiltUri.VocabularyEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        if(row != 0) getContext().getContentResolver().notifyChange(uri,null);
        // Student: return the actual rows deleted
        return 0;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Student: This is a lot like the delete function.  We return the number of rows impacted
        // by the update.
        final SQLiteDatabase db= myDatabase;
        final int match=sUriMatcher.match(uri);
        int row;

        switch (match){
            case UNIT:
                row=db.update(VocabularyBuiltUri.UnitEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case VOCABULARY:
                row= db.update(VocabularyBuiltUri.VocabularyEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        if(row != 0)
            getContext().getContentResolver().notifyChange(uri,null);
        return row;
    }

//    @Override
//    public int bulkInsert(Uri uri, ContentValues[] values) {
//        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
//        final int match = sUriMatcher.match(uri);
//        switch (match) {
//            case WEATHER:
//                db.beginTransaction();
//                int returnCount = 0;
//                try {
//                    for (ContentValues value : values) {
//                        normalizeDate(value);
//                        long _id = db.insert(VocabularyBuiltUri.WeatherEntry.TABLE_NAME, null, value);
//                        if (_id != -1) {
//                            returnCount++;
//                        }
//                    }
//                    db.setTransactionSuccessful();
//                } finally {
//                    db.endTransaction();
//                }
//                getContext().getContentResolver().notifyChange(uri, null);
//                return returnCount;
//            default:
//                return super.bulkInsert(uri, values);
//        }
//    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(16)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}