package com.example.healer.ieltsvocabulary;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.healer.ieltsvocabulary.adapter.MainAdapter;

public class LessonActivity extends FragmentActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        viewPager = (ViewPager)findViewById(R.id.view_paper);
        MainAdapter ma = new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(ma);
    }

}
