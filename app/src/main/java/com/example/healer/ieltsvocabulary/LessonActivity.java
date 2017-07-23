package com.example.healer.ieltsvocabulary;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.example.healer.ieltsvocabulary.adapter.MainAdapter;
import com.example.healer.ieltsvocabulary.fragment.LessonFragment;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    int lessonMax;
    int lessonPosCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataLesson");
        ArrayList<Vocabulary> vocabularyLesson = (ArrayList<Vocabulary>) bundle.getSerializable("vocabularyLesson");
        lessonMax = bundle.getInt("lessonMax");
        lessonPosCurrent = bundle.getInt("lessonCurrent");
        viewPager = (ViewPager)findViewById(R.id.view_paper);
        viewPager.setOffscreenPageLimit(5);
        Log.d("fragment", String.valueOf(viewPager.getCurrentItem()));
        viewPager.setOnPageChangeListener(this);
        MainAdapter ma = new MainAdapter(getSupportFragmentManager(),vocabularyLesson);
        viewPager.setAdapter(ma);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int item = viewPager.getCurrentItem();
        Fragment f = (Fragment) getSupportFragmentManager().getFragments().get(item);
        f.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("pos",String.valueOf(position));
        if(position == 4 && (lessonPosCurrent + 1) == lessonMax){
            LessonFragment.studiedWord = true;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
