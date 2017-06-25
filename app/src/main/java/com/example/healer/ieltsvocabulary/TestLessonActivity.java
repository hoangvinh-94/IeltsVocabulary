package com.example.healer.ieltsvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.fragment.TestListeningFragment;
import com.example.healer.ieltsvocabulary.fragment.TestMeaningByListenFragment;
import com.example.healer.ieltsvocabulary.fragment.TestMeaningFragment_1;
import com.example.healer.ieltsvocabulary.fragment.TestUnitFragment;
import com.example.healer.ieltsvocabulary.fragment.TestWordByMeaningFragment;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;

public class TestLessonActivity extends AppCompatActivity
        implements TestMeaningFragment_1.TestMeaningFragment_1Interface,
        TestMeaningByListenFragment.TestMeaningByListeningInterface,
        TestListeningFragment.TestListeningInterface,
        TestWordByMeaningFragment.TestWordByMeaningInterface
{
    boolean answer = false, meaning1 = false, meaningByListening = false, mListening = false, wordByMeaning = false;
    int test[] = {0  //0. meaning 1
            , 0    //1. meaning by listening
            , 0    //2. mListening
            , 0    //3. word by meaning
            , 0    //4. speech words
            , 0};  //5. speech phrase
    ArrayList<Vocabulary> vocabularyLesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lesson);

        Intent intent = getIntent();
        vocabularyLesson = (ArrayList<Vocabulary>) intent.getBundleExtra("dataLesson").getSerializable("vocabularyLesson");
        int idUnit = intent.getBundleExtra("dataLesson").getInt("idUnit");
        Log.d("sadfas",String.valueOf(vocabularyLesson.get(0).getWord()));



//        viewPager = (ViewPager)findViewById(R.id.view_paper);
//        MainAdapter ma = new MainAdapter(getSupportFragmentManager(),vocabularyLesson);
//        viewPager.setAdapter(ma);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            Bundle args = new Bundle();
            args.putSerializable("vocabularyLesson", vocabularyLesson);

            // Create a new Fragment to be placed in the activity layout
            TestMeaningFragment_1 firstFragment = new TestMeaningFragment_1();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(args);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }
    public void changeFragment(View view){
        if(view == findViewById(R.id.nextBtn)){
            if(meaning1 && test[0] == 1){
                test[0] = 0;
                TestMeaningByListenFragment newFragment = new TestMeaningByListenFragment();
                Bundle args = new Bundle();
                args.putSerializable("vocabularyLesson", vocabularyLesson);

                newFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
            else {
                if (meaningByListening && test[1] == 1) {
                    test[1] = 0;
                    TestListeningFragment newFragment = new TestListeningFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("vocabularyLesson", vocabularyLesson);

                    newFragment.setArguments(args);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                } else {
                    if (mListening && test[2] == 1) {
                        test[2] = 0;
                        TestWordByMeaningFragment newFragment = new TestWordByMeaningFragment();
                        Bundle args = new Bundle();
                        args.putSerializable("vocabularyLesson", vocabularyLesson);

                        newFragment.setArguments(args);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                    } else {
                        if (wordByMeaning && test[3] == 1) {
                            test[3] = 0;
                            TestUnitFragment newFragment = new TestUnitFragment();
                            Bundle args = new Bundle();
                            args.putSerializable("vocabularyLesson", vocabularyLesson);

                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            // Replace whatever is in the fragment_container view with this fragment,
                            // and add the transaction to the back stack so the user can navigate back
                            transaction.replace(R.id.fragment_container, newFragment);
                            transaction.addToBackStack(null);

                            // Commit the transaction
                            transaction.commit();
                        }
                    }
                }
            }
//                        else {
//                            if((wordSpeechTest && test[3] == 1 && test[4] == -1)||
//                                    (wordSpeechTest && test[3] == 1 && test[4] != -1 && vocabulary.isOneWord())){
//                                test[3] = 0;
//                                test[4] = 0;
//                                FragmentPhraseSpeech newFragment = new FragmentPhraseSpeech();
//                                Bundle args = new Bundle();
//                                args.putSerializable("WordTest", vocabulary);
//
//                                newFragment.setArguments(args);
//                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                                // Replace whatever is in the fragment_container view with this fragment,
//                                // and add the transaction to the back stack so the user can navigate back
//                                transaction.replace(R.id.fragment_container, newFragment);
//                                transaction.addToBackStack(null);
//
//                                // Commit the transaction
//                                transaction.commit();
//                            }
//                            else {
//                                if(phraseSpeechTest && test[5] == 1){
//                                    test[5] = 0;
//                                    Toast.makeText(this,
//                                            "You has completed the test!", Toast.LENGTH_LONG).show();
//                                    CountDownTimer timer1 = new CountDownTimer(1000,1000) {
//                                        @Override
//                                        public void onTick(long millisUntilFinished) {
//                                        }
//
//                                        @Override
//                                        public void onFinish() {
//                                            //setTrue(PICTURE_WORD,Topic);
//                                        }
//                                    }.start();
//
//                                    Intent intent = new Intent(this, Compare.class);
//                                    intent.putExtra("Vocabulary", vocabulary);
//                                    startActivity(intent);
//
//                                }
//                                else {
//                                    Toast.makeText(this,
//                                            "You answer: "+answer, Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        }
//
//                    }
//
//                }
//
//            }

        }
    }

    @Override
    public boolean onReturnAnswer(boolean answer) {
        meaning1 = answer;
        test[0] = 1;
        return meaning1;
    }

    @Override
    public boolean onReturnAnswerMeaningByListening(boolean answer) {
        meaningByListening = answer;
        test[1] = 1;
        return meaningByListening;
    }

    @Override
    public boolean onReturnAnswerListening(boolean answer) {
        mListening = answer;
        test[2] = 1;
        return mListening;
    }

    @Override
    public boolean onReturnAnswerWordByMeaning(boolean answer) {
        wordByMeaning = answer;
        test[3] = 1;
        return wordByMeaning;
    }
}
