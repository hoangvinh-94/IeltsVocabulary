package com.example.healer.ieltsvocabulary.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.MeanController;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;
import java.util.Random;

public class TestWordByMeaningFragment extends Fragment {
    ArrayList<Vocabulary> vocabularyLesson;
    Button word1, word2, word3, word4;
    TextView word;
    ImageButton listenBtn;
    String answer;
    TestWordByMeaningInterface mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_test_word_by_meaning, container, false);
        word = (TextView) rootView.findViewById(R.id.test_meaning_vocabulary);
        word1 = (Button)rootView.findViewById(R.id.test_compare_word1);
        word2 = (Button)rootView.findViewById(R.id.test_compare_word2);
        word3 = (Button)rootView.findViewById(R.id.test_compare_word3);
        word4 = (Button)rootView.findViewById(R.id.test_compare_word4);


        listenBtn = (ImageButton)rootView.findViewById(R.id.tracnghiem_word_Btn);
        Bundle arguments = getArguments();
        if (arguments != null) {
            vocabularyLesson = (ArrayList<Vocabulary>)arguments.getSerializable("vocabularyLesson");
            settingQuestion();
        }
        else {
            return rootView;
        }

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
        answer = vocabularyLesson.get(wordNum).getWord();
        ArrayList<Mean> means = (new MeanController(this.getContext()).getMeanById(vocabularyLesson.get(wordNum).getId()));
        word.setText(means.get(0).getMean());
        setMean[wordNum] = 1;
        Random p = new Random();
        int pos = p.nextInt(4);
        if(pos == 0) {
            word1.setText( vocabularyLesson.get(wordNum).getWord());
            setBtn[0] = 1;
        }
        if(pos == 1) {
            word2.setText( vocabularyLesson.get(wordNum).getWord());
            setBtn[1] = 1;
        }
        if(pos == 2) {
            word3.setText( vocabularyLesson.get(wordNum).getWord());
            setBtn[2] = 1;
        }
        if(pos == 3) {
            word4.setText( vocabularyLesson.get(wordNum).getWord());
            setBtn[3] = 1;
        }
        setPos[pos] = 1;

        for(int i = 0; i < 3;i++){
            Random ques = new Random();
            int num = ques.nextInt(5);
            int p1 = ques.nextInt(4);
            while(setMean[num] == 1 || setPos[p1] == 1){
                num = ques.nextInt(5);
                p1 = ques.nextInt(4);
            }
            setMean[num] = 1;
            setPos[p1] = 1;
            if(setBtn[p1] == 0 && p1 == 0) {
                word1.setText( vocabularyLesson.get(num).getWord());
                setBtn[0] = 1;

            }
            if(setBtn[p1] == 0 && p1 == 1) {
                word2.setText( vocabularyLesson.get(num).getWord());
                setBtn[1] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 2) {
                word3.setText( vocabularyLesson.get(num).getWord());
                setBtn[2] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 3) {
                word4.setText( vocabularyLesson.get(num).getWord());
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

                    mCallback.onReturnAnswerWordByMeaning(true);
                }
                else {
                    //     Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerWordByMeaning(false);
                }
                break;
            case R.id.test_compare_word2:
                if((word2.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                    //  Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerWordByMeaning(true);
                }
                else {
                    //      Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerWordByMeaning(false);
                }
                break;
            case R.id.test_compare_word3:
                if((word3.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                    //    Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerWordByMeaning(true);
                }
                else {
                    //   Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerWordByMeaning(false);
                }
                break;
            case R.id.test_compare_word4:
                if((word4.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                    //  Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerWordByMeaning(true);
                }
                else {
                    //  Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerWordByMeaning(false);
                }
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }


    //    // Container Activity must implement this interface
    public interface TestWordByMeaningInterface {
        public boolean onReturnAnswerWordByMeaning(boolean answer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TestWordByMeaningInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TestWordInterface");
        }
    }

}
