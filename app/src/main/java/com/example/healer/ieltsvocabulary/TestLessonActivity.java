package com.example.healer.ieltsvocabulary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healer.ieltsvocabulary.fragment.TestListeningFragment;
import com.example.healer.ieltsvocabulary.fragment.TestMeaningByListenFragment;
import com.example.healer.ieltsvocabulary.fragment.TestMeaningFragment;
import com.example.healer.ieltsvocabulary.fragment.TestUnitFragment;
import com.example.healer.ieltsvocabulary.fragment.TestWordByMeaningFragment;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

public class TestLessonActivity extends AppCompatActivity
        implements TestMeaningFragment.TestMeaningFragment_1Interface,
        TestMeaningByListenFragment.TestMeaningByListeningInterface,
        TestListeningFragment.TestListeningInterface,
        TestWordByMeaningFragment.TestWordByMeaningInterface,TestUnitFragment.TestUnitFragmentInterface
{
    boolean Answer = false, meaning1 = false, meaningByListening = false, mListening = false, wordByMeaning = false, wordInBlank = false;
    int test[] = {0  //0. meaning 1
            , 0    //1. meaning by listening
            , 0    //2. mListening
            , 0    //3. word by meaning
            , 0    //4. speech words
            , 0};  //5. speech phrase
    ArrayList<Vocabulary> vocabularyLesson;
    int lessonNumber;
    Button button;
    EditText result;
    int score = 0;
    AnimationDrawable animation;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lesson);
        ActionBar bar = getSupportActionBar();
        bar.setHomeAsUpIndicator(R.drawable.home);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Ielts Vocabulary");
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataLesson");
        vocabularyLesson = (ArrayList<Vocabulary>) bundle.getSerializable("vocabularyLesson");
        lessonNumber = bundle.getInt("lesson");

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            Bundle args = new Bundle();
            args.putSerializable("vocabularyLesson", vocabularyLesson);
            args.putInt("lesson",lessonNumber);

            // Create a new Fragment to be placed in the activity layout
            TestMeaningFragment firstFragment = new TestMeaningFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(args);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void changeFragment(View view) {
        if (view == findViewById(R.id.nextBtn)) {
            if (Answer) {
                Answer = false;
                if (test[0] == 1) {
                    score++;
                    test[0] = 0;
                    button.setBackgroundResource(R.drawable.check_correct);
                    animation = (AnimationDrawable) button.getBackground();
                    animation.start();
                    TestMeaningByListenFragment newFragment = new TestMeaningByListenFragment();
                    replaceFragment(newFragment);
                }
                if (test[1] == 1) {
                    score++;
                    test[1] = 0;
                    button.setBackgroundResource(R.drawable.check_correct);
                    animation = (AnimationDrawable) button.getBackground();
                    animation.start();
                    TestListeningFragment newFragment = new TestListeningFragment();
                    replaceFragment(newFragment);
                }
                if (test[2] == 1) {
                    score++;
                    test[2] = 0;
                    result.setTextColor(Color.parseColor("#00FF00"));
                    TestWordByMeaningFragment newFragment = new TestWordByMeaningFragment();
                    replaceFragment(newFragment);
                }
                if (test[3] == 1) {
                    score++;
                    test[3] = 0;
                    button.setBackgroundResource(R.drawable.check_correct);
                    animation = (AnimationDrawable) button.getBackground();
                    animation.start();
                    TestUnitFragment newFragment = new TestUnitFragment();
                    replaceFragment(newFragment);
                }
                if (test[4] == 1) {
                    score++;
                    test[4] = 0;
                    button.setBackgroundResource(R.drawable.check_correct);
                    animation = (AnimationDrawable) button.getBackground();
                    animation.start();
                    Bundle args = new Bundle();
                    Intent intent = new Intent(this, ScoreTestActivity.class);
                    args.putSerializable("vocabularyLesson", vocabularyLesson);
                    args.putInt("lesson", lessonNumber);
                    args.putInt("score", score);
                    intent.putExtra("data", args);
                    startActivity(intent);
                }
            } else {
                if (test[0] == 1) {
                    test[0] = 0;
                    button.setBackgroundResource(R.drawable.my_btn_bg_incorrect);
                    TestMeaningByListenFragment newFragment = new TestMeaningByListenFragment();
                    replaceFragment(newFragment);
                }
                if (test[1] == 1) {
                    test[1] = 0;
                    button.setBackgroundResource(R.drawable.my_btn_bg_incorrect);
                    TestListeningFragment newFragment = new TestListeningFragment();
                    replaceFragment(newFragment);
                }
                if (test[2] == 1) {
                    test[2] = 0;
                    result.setTextColor(Color.parseColor("#FF0000"));
                    TestWordByMeaningFragment newFragment = new TestWordByMeaningFragment();
                    replaceFragment(newFragment);
                }
                if (test[3] == 1) {
                    test[3] = 0;
                    button.setBackgroundResource(R.drawable.my_btn_bg_incorrect);
                    TestUnitFragment newFragment = new TestUnitFragment();
                    replaceFragment(newFragment);
                }
                if (test[4] == 1) {
                    test[4] = 0;
                    button.setBackgroundResource(R.drawable.my_btn_bg_incorrect);
                    Bundle args = new Bundle();
                    Intent intent = new Intent(this, ScoreTestActivity.class);
                    args.putSerializable("vocabularyLesson", vocabularyLesson);
                    args.putInt("lesson", lessonNumber);
                    args.putInt("score", score);
                    intent.putExtra("data", args);
                    startActivity(intent);
                }
            }
        }
    }
    public void replaceFragment(final Fragment newFragment){
        timer = new CountDownTimer(1000 * 2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Bundle args = new Bundle();
                args.putSerializable("vocabularyLesson", vocabularyLesson);
                args.putInt("lesson",lessonNumber);
                newFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        }.start();

    }

    @Override
    public boolean onReturnAnswer(boolean answer, Button B) {
        Answer = answer;
        button = B;
        test[0] = 1;
        return Answer;
    }

    @Override
    public boolean onReturnAnswerMeaningByListening(boolean answer, Button B) {
        Answer = answer;
        button = B;
        test[1] = 1;
        return Answer;
    }

    @Override
    public boolean onReturnAnswerListening(boolean answer, EditText E) {
        Answer = answer;
        result = E;
        test[2] = 1;
        return Answer;
    }

    @Override
    public boolean onReturnAnswerWordByMeaning(boolean answer, Button B) {
        Answer = answer;
        button = B;
        test[3] = 1;
        return Answer;
    }

    @Override
    public boolean onReturnAnswerBlank(boolean answer, Button B) {
        Answer = answer;
        button = B;
        test[4] = 1;
        return Answer;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer !=null){
            timer.onFinish();
        }
        this.finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(timer !=null){
            timer.onFinish();
        }
        this.finish();

    }
}
