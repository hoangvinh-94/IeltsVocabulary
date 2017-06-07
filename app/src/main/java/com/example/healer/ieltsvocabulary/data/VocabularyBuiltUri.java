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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

/**
 * Defines table and column names for the weather database.
 */
public class VocabularyBuiltUri {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.healer.ieltsvocabulary.data.IeltsProvider";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_UNIT = "UNITS";
    public static final String PATH_VOCABULARY = "VOCABULARYS";

    /*
        Inner class that defines the table contents of the location table
        Students: This is where you will add the strings.  (Similar to what has been
        done for WeatherEntry)
     */

    public static final class UnitEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_UNIT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UNIT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_UNIT;

        public static Uri buildUnitUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static final String TABLE_NAME = "UNITS";

        public static final String COLUMN_ID = "unitID";

        public static final String COLUMN_NAME= "name";

        public static final String COLUMN_NUM_WORD= "numberOfWord";

        public static final String COLUMN_AVATAR_URI= "avatarUrl";

    }

    /* Inner class that defines the table contents of the weather table */
    public static final class VocabularyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VOCABULARY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VOCABULARY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VOCABULARY;


        public static Uri buildVocabularyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

//        /*
//            Student: Fill in this buildWeatherLocation function
//         */
//        public static Uri build(String locationSetting) {
//            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
//        }
//
//        public static Uri buildWeatherLocationWithStartDate(
//                String locationSetting, long startDate) {
//            long normalizedDate = normalizeDate(startDate);
//            return CONTENT_URI.buildUpon().appendPath(locationSetting)
//                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
//        }
//
//        public static Uri buildWeatherLocationWithDate(String locationSetting, long date) {
//            return CONTENT_URI.buildUpon().appendPath(locationSetting)
//                    .appendPath(Long.toString(normalizeDate(date))).build();
//        }
//
//        public static String getLocationSettingFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//      Get the audioUri from uri
        public static String getAudioFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
//
//        public static long getStartDateFromUri(Uri uri) {
//            String dateString = uri.getQueryParameter(COLUMN_DATE);
//            if (null != dateString && dateString.length() > 0)
//                return Long.parseLong(dateString);
//            else
//                return 0;
//        }


        public static final String TABLE_NAME = "VOCABULARYS";

        // Column with the foreign key into the unit table.
        public static final String COLUMN_UNIT_ID = "unitId";
        // Word
        public static final String COLUMN_WORD = "word";
        // Word id
        public static final String COLUMN_WORD_ID = "vocabularyID";
        // Phonenic of the word
        public static final String COLUMN_PHONIC = "phonetic";
        // Detail of word,
        public static final String COLUMN_DETAIL = "detail";
        //Short mean
        public static final String COLUMN_SHORT_MEAN = "shortMean";
        // Status
        public static final String COLUMN_STATUS = "status";
        // Important
        public static final String COLUMN_IMPORTANT = "important";
        // Audio of the word
        public static final String COLUMN_AUDIO_URI = "audioUri";

    }
}
