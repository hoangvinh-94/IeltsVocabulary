package com.example.healer.ieltsvocabulary;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.healer.ieltsvocabulary.game.GameActivity;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

public class ScoreTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_test);
        ActionBar bar = getSupportActionBar();
        bar.setHomeAsUpIndicator(R.drawable.home);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Ielts Vocabulary");
        TextView numWord = (TextView) findViewById(R.id.numWord);
        Button tryAgain = (Button) findViewById(R.id.tryAgain);
        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        final ArrayList<Vocabulary> vocabularies = ( ArrayList<Vocabulary>)bundle.getSerializable("vocabularyLesson");
        int score = bundle.getInt("score");
        int numLesson = bundle.getInt("lesson");
        numWord.setText(score+"/5 correct");

        TableRow row = (TableRow) findViewById(R.id.tableRow);

        for(int i = 0; i<score ;i++){
            ImageView imgStart = new ImageView(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(80,80);
            imgStart.setLayoutParams(params);
            imgStart.setImageResource(R.drawable.star);
            imgStart.setScaleType(ImageView.ScaleType.FIT_XY);
            row.addView(imgStart);
        }
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),TestLessonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("vocabularyLesson",vocabularies);
                intent1.putExtra("dataLesson",bundle);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
