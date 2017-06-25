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
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.MeanController;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TestMeaningByListenFragment extends Fragment {
    ArrayList<Vocabulary> vocabularyLesson;
    Button word1, word2, word3, word4;
    String word;
    ImageButton listenBtn;
    String answer;
    TextToSpeech textToSpeech;
    int resultSpeech;
    public boolean result = false;
    TestMeaningByListeningInterface mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_test_meaning_by_listen, container, false);
        word1 = (Button)rootView.findViewById(R.id.test_compare_word1);
        word2 = (Button)rootView.findViewById(R.id.test_compare_word2);
        word3 = (Button)rootView.findViewById(R.id.test_compare_word3);
        word4 = (Button)rootView.findViewById(R.id.test_compare_word4);

        word = "";
        listenBtn = (ImageButton)rootView.findViewById(R.id.tracnghiem_word_Btn);
        Bundle arguments = getArguments();
        if (arguments != null) {
            vocabularyLesson = (ArrayList<Vocabulary>)arguments.getSerializable("vocabularyLesson");
            settingQuestion();
        }
        else {
            return rootView;
        }


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
        //Handle when volumn button is clicked
        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTextToVoiceTest(listenBtn);
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
        word = vocabularyLesson.get(wordNum).getWord();
        setMean[wordNum] = 1;
        Random p = new Random();
        int pos = p.nextInt(4);
        if(pos == 0) {
            word1.setText(  means.get(0).getMean());
            setBtn[0] = 1;
        }
        if(pos == 1) {
            word2.setText(means.get(0).getMean());
            setBtn[1] = 1;
        }
        if(pos == 2) {
            word3.setText(means.get(0).getMean());
            setBtn[2] = 1;
        }
        if(pos == 3) {
            word4.setText(means.get(0).getMean());
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
                word1.setText( means.get(0).getMean());
                setBtn[0] = 1;

            }
            if(setBtn[p1] == 0 && p1 == 1) {
                word2.setText(means.get(0).getMean());
                setBtn[1] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 2) {
                word3.setText( means.get(0).getMean());
                setBtn[2] = 1;
            }
            if(setBtn[p1] == 0 && p1 == 3) {
                word4.setText( means.get(0).getMean());
                setBtn[3] = 1;
            }

        }

    }
    //Do text to voice
    public void doTextToVoiceTest(View v){
        switch (v.getId()){
            case R.id.tracnghiem_word_Btn:
                if(resultSpeech == TextToSpeech.LANG_NOT_SUPPORTED || resultSpeech == TextToSpeech.LANG_MISSING_DATA){
                    Toast.makeText(getContext(),
                            "You device is not support feature!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),word, Toast.LENGTH_LONG).show();
                    textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            default:
                Toast.makeText(getContext(),
                        "Error! You device cant speak this word!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //Checked the answer
    public void onClickAnswer(View v) {
        switch (v.getId()) {
            case R.id.test_compare_word1:
                if((word1.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                          Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerMeaningByListening(true);
                }
                else {
                         Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerMeaningByListening(false);
                }
                break;
            case R.id.test_compare_word2:
                if((word2.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                      Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerMeaningByListening(true);
                }
                else {
                          Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerMeaningByListening(false);
                }
                break;
            case R.id.test_compare_word3:
                if((word3.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                        Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerMeaningByListening(true);
                }
                else {
                       Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerMeaningByListening(false);
                }
                break;
            case R.id.test_compare_word4:
                if((word4.getText().toString().toLowerCase()).equals(answer.toLowerCase())){
                      Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();

                    mCallback.onReturnAnswerMeaningByListening(true);
                }
                else {
                      Toast.makeText(getContext(), "1 FALSE!", Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerMeaningByListening(false);
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
    //    // Container Activity must implement this interface
    public interface TestMeaningByListeningInterface {
        public boolean onReturnAnswerMeaningByListening(boolean answer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TestMeaningByListeningInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TestWordInterface");
        }
    }

}