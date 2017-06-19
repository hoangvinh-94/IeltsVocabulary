package com.example.healer.ieltsvocabulary;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.healer.ieltsvocabulary.adapter.HomeAdapter;
import com.example.healer.ieltsvocabulary.controller.UnitController;
import com.example.healer.ieltsvocabulary.model.Unit;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        UnitController uc = new UnitController(this);
        uc.saveImageToDB();
        Log.d("vinh", "sadfasf");
        HomeAdapter homeAdapter = null;
        ArrayList<Unit> listUnit = null;
        listUnit = new ArrayList<Unit>();
        listUnit = UnitController.loadData(this);
        //listUnit.add(new Unit(1,"asdfas",12,"images/category1.jpg"));
        homeAdapter = new HomeAdapter(this,R.layout.custom_home, listUnit);
        GridView gv = (GridView) findViewById(R.id.listUnit);
        gv.setAdapter(homeAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
