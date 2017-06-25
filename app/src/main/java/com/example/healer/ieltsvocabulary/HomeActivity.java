package com.example.healer.ieltsvocabulary;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.adapter.HomeAdapter;
import com.example.healer.ieltsvocabulary.controller.ImageController;
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
        HomeAdapter homeAdapter = null;

        ArrayList<Unit> listUnit = new ArrayList<Unit>();
        UnitController uc = new UnitController(this);
        listUnit = uc.loadData();
        ImageController ic = new ImageController(this);
       // Bitmap bit = ic.loadImg();
      //  if(bit == null) Log.d("null", "null");

        String []image = {"images/accommodate.jpg","images/adolescence.jpg","images/adopt.jpg","images/adulthood.jpg","images/bond.jpg","images/brotherhood.jpg"};
        for(int i=0; i < image.length; i++){
            ic.saveImage(image[i],1);
        }
        String []image1 = {"images/ability.jpg","images/abstract.jpg","images/acquire.jpg","images/adolescent.jpg","images/behaviour.jpg"};
        for(int i=0; i < image.length; i++){
            ic.saveImage(image[i],2);
        }
        listUnit.add(new Unit(1,"asdfas",12,"images/category1.jpg"));
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
