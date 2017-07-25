package com.example.healer.ieltsvocabulary.game;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
        ActionBar bar = getSupportActionBar();
        bar.setHomeAsUpIndicator(R.drawable.home);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Ielts Vocabulary");
        Button tryAgain = (Button)findViewById(R.id.tryAgain);
        final Intent intent = getIntent();
        int score = intent.getBundleExtra("data").getInt("score");
        final ArrayList<Vocabulary> vocabularies = ( ArrayList<Vocabulary>)intent.getBundleExtra("data").getSerializable("vocabularys");
        Log.d("score",String.valueOf(score));
        TableRow row = (TableRow) findViewById(R.id.tableRow);
        for(int i = 0; i<score ;i++){
            ImageView imgStart = new ImageView(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(60,60);
            imgStart.setLayoutParams(params);
            imgStart.setImageResource(R.drawable.star);
            imgStart.setScaleType(ImageView.ScaleType.FIT_XY);
            row.addView(imgStart);
        }
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("vocabularyLesson",vocabularies);
                intent1.putExtra("dataLesson",bundle);
                startActivity(intent1);
            }
        });
        stopService(new Intent(this,MyServices.class));
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
