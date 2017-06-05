package com.example.healer.ieltsvocabulary;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.healer.ieltsvocabulary.adapter.MainAdapter;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

public class LessonActivity extends FragmentActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        Intent intent = getIntent();
        ArrayList<Vocabulary> vocabularyLesson = (ArrayList<Vocabulary>) intent.getBundleExtra("dataLesson").getSerializable("vocabularyLesson");
        int idUnit = intent.getBundleExtra("dataLesson").getInt("idUnit");
        Log.d("sadfas",String.valueOf(vocabularyLesson.get(0).getWord()));
        viewPager = (ViewPager)findViewById(R.id.view_paper);
        MainAdapter ma = new MainAdapter(getSupportFragmentManager(),vocabularyLesson);
        viewPager.setAdapter(ma);
    }

}
