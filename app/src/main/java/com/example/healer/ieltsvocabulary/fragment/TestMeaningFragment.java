package com.example.healer.ieltsvocabulary.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.MeanController;
import com.example.healer.ieltsvocabulary.controller.UnitController;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Unit;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TestMeaningFragment extends Fragment {
    ArrayList<Vocabulary> vocabularyLesson;
    int lessonNumber;
    Button word1, word2, word3, word4;
    TextView word;
    String answer;
    TextToSpeech textToSpeech;
    int resultSpeech;
    public boolean result = false;
    TestMeaningFragment_1Interface mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.test_meaning_fragment_1, container, false);
        word = (TextView) rootView.findViewById(R.id.test_meaning_vocabulary);
        word1 = (Button)rootView.findViewById(R.id.test_compare_word1);
        word2 = (Button)rootView.findViewById(R.id.test_compare_word2);
        word3 = (Button)rootView.findViewById(R.id.test_compare_word3);
        word4 = (Button)rootView.findViewById(R.id.test_compare_word4);
        TextView unitTitle = (TextView) rootView.findViewById(R.id.unitTitle);
        TextView lesson = (TextView) rootView.findViewById(R.id.lessonNumber);

        Bundle arguments = getArguments();
        if (arguments != null) {
            vocabularyLesson = (ArrayList<Vocabulary>)arguments.getSerializable("vocabularyLesson");
            lessonNumber = arguments.getInt("lesson");
            settingQuestion();
        }
        else {
            return rootView;
        }
        Unit unit = (new UnitController(this.getActivity())).getUnit(vocabularyLesson.get(0).getUnitId());
        unitTitle.setText(unit.getName());
        lesson.setText("Lesson "+String.valueOf(lessonNumber+1));
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    resultSpeech = textToSpeech.setLanguage(Locale.US);
                }
                else{
                    Toast.makeText(getContext(),"Your device cannot read this word!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Checked result when user choose an answer
        word1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word1.setBackgroundColor(Color.parseColor("#0288D1"));
                word1.setTextColor(getResources().getColor(R.color.white) );
                settingNormalButton(word2, word3, word4);
                onClickAnswer(word1);
            }
        });
        word2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word2.setBackgroundColor(Color.parseColor("#0288D1"));
                word2.setTextColor(getResources().getColor(R.color.white) );
                settingNormalButton(word1, word3, word4);
                onClickAnswer(word2);
            }
        });

            word3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    word3.setBackgroundColor(Color.parseColor("#0288D1"));
                    word3.setTextColor(getResources().getColor(R.color.white) );
                    settingNormalButton(word2, word1, word4);
                    onClickAnswer(word3);
                }
            });
            word4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    word4.setBackgroundColor(Color.parseColor("#0288D1"));
                    word4.setTextColor(getResources().getColor(R.color.white) );
                    settingNormalButton(word2, word3, word1);
                    onClickAnswer(word4);
                }
            });

        return rootView;
    }

    //Method set background and text color of other button except button is clicked.
    private void settingNormalButton(Button button1, Button button2, Button button3){
        button1.setBackgroundResource(R.drawable.my_btn_bg);
        button1.setTextColor(getResources().getColor(R.color.test_dark_blue) );
        button2.setBackgroundResource(R.drawable.my_btn_bg);
        button2.setTextColor(getResources().getColor(R.color.test_dark_blue) );
        button3.setBackgroundResource(R.drawable.my_btn_bg);
        button3.setTextColor(getResources().getColor(R.color.test_dark_blue) );
    }

    //Method set which button is the right answer, random the question
    private void settingQuestion(){
        int[] setMean = {0, 0, 0, 0, 0};
        int[] setPos = {0, 0, 0, 0};
        int[] setBtn = {0, 0, 0, 0};

        Random ans = new Random();
        int wordNum = ans.nextInt(5);
        ArrayList<Mean> means = (new MeanController(this.getContext()).getMeanById(vocabularyLesson.get(wordNum).getId()));
        answer = means.get(0).getMean();
        word.setText(vocabularyLesson.get(wordNum).getWord());

        // Random position
        setMean[wordNum] = 1;
        Random p = new Random();
        int pos = p.nextInt(4);
        if(pos == 0) {
            word1.setText( means.get(0).getMean());
            setBtn[0] = 1;
        }
        if(pos == 1) {
            word2.setText( means.get(0).getMean());
            setBtn[1] = 1;
        }
        if(pos == 2) {
            word3.setText( means.get(0).getMean());
            setBtn[2] = 1;
        }
        if(pos == 3) {
            word4.setText( means.get(0).getMean());
            setBtn[3] = 1;
        }
        setPos[pos] = 1;

        // Random 4 question
        for(int i = 0; i < 3;i++){
            Random ques = new Random();
            int num = ques.nextInt(5);
            int p1 = ques.nextInt(4);
            while(setMean[num] == 1 || setPos[p1] == 1){
                num = ques.nextInt(5);
                p1 = ques.nextInt(4);
            }
            ArrayList<Mean> means1 = (new MeanController(this.getContext()).getMeanById(vocabularyLesson.get(num).getId()));
            setMean[num] = 1;
            setPos[p1] = 1;
            if(setBtn[p1] == 0 && p1 == 0) {
                word1.setText( means1.get(0).getMean());
                setBtn[0] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 1) {
                word2.setText( means1.get(0).getMean());
                setBtn[1] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 2) {
                word3.setText( means1.get(0).getMean());
                setBtn[2] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 3) {
                word4.setText( means1.get(0).getMean());
                setBtn[3] = 1;
            }
        }
    }

    //Checked the answer
    public void onClickAnswer(View v) {
        switch (v.getId()) {
            case R.id.test_compare_word1:
                if((word1.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                   //      Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswer(true,word1);
                }
                else {
                  //     Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswer(false,word1);
                }
                break;
            case R.id.test_compare_word2:
                if((word2.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                  //  Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswer(true,word2);
                }
                else {
                 //      Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswer(false,word2);
                }
                break;
            case R.id.test_compare_word3:
                if((word3.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                //    Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswer(true,word3);
                }
                else {
                 //   Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswer(false,word3);
                }
                break;
            case R.id.test_compare_word4:
                if((word4.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                  //  Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswer(true,word4);
                }
                else {
                  //  Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswer(false,word4);
                }
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }

    //Stop and shutdown text to speech
    public void onDestroy() {
        super.onDestroy();
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
   // Container Activity must implement this interface
    public interface TestMeaningFragment_1Interface {
        public boolean onReturnAnswer(boolean answer,Button B);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TestMeaningFragment_1Interface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TestWordInterface");
        }
    }



}
