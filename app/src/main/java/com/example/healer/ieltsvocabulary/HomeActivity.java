package com.example.healer.ieltsvocabulary;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import com.example.healer.ieltsvocabulary.adapter.HomeAdapter;
import com.example.healer.ieltsvocabulary.data.VocabularyBuiltUri;

public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int FORECAST_LOADER = 0;
    private HomeAdapter mHomeAdapter;
    private GridView mGridView;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";
    private static final String[] UNIT_COLUMNS = {

            VocabularyBuiltUri.UnitEntry.TABLE_NAME + "." + VocabularyBuiltUri.UnitEntry.COLUMN_ID,
            VocabularyBuiltUri.UnitEntry.COLUMN_NAME,
            VocabularyBuiltUri.UnitEntry.COLUMN_NUM_WORD,
            VocabularyBuiltUri.UnitEntry.COLUMN_AVATAR_URI
    };

    // These indices are tied to UNIT_COLUMNS.  If UNIT_COLUMNS changes, these
    // must change.
    public static final int COL_UNIT_ID = 0;
    public static final int COL_UNIT_NAME = 1;
    public static final int COL_NUM_OF_WORD = 2;
    public static final int COL_AVATAR_URI = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Uri avatarUri = VocabularyBuiltUri.UnitEntry.CONTENT_URI;
        Cursor cur = this.getContentResolver().query(avatarUri,
                UNIT_COLUMNS, null, null, null);
        mHomeAdapter = new HomeAdapter(this, cur, 0);

        mGridView = (GridView) findViewById(R.id.listUnit);

        mGridView.setAdapter(mHomeAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri avatarUri = VocabularyBuiltUri.UnitEntry.CONTENT_URI;
        String[] projection = new String[] { VocabularyBuiltUri.UnitEntry.COLUMN_AVATAR_URI };
        Log.e("onCreateLoader: ", "thach thanh binh");
        return new CursorLoader(this,
                avatarUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e("on Fisnished", "this is on finished");
        mHomeAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

