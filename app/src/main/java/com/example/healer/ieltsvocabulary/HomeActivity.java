package com.example.healer.ieltsvocabulary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.healer.ieltsvocabulary.adapter.HomeAdapter;
import com.example.healer.ieltsvocabulary.controller.UnitController;
import com.example.healer.ieltsvocabulary.data.LoadDataBaseSQLiteHelper;
import com.example.healer.ieltsvocabulary.data.VocabularyBuiltUri;
import com.example.healer.ieltsvocabulary.fragment.LessonFragment;
import com.example.healer.ieltsvocabulary.model.Unit;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeAdapter homeAdapter = null;
        ArrayList<Unit> listUnit = null;
        listUnit = new ArrayList<Unit>();
        listUnit = UnitController.loadData(this);
        homeAdapter = new HomeAdapter(this,R.layout.custom_home, listUnit);
        GridView gv = (GridView) findViewById(R.id.listUnit);
        gv.setAdapter(homeAdapter);
    }

}
