package com.example.healer.ieltsvocabulary.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.MeanController;
import com.example.healer.ieltsvocabulary.controller.PracticeController;
import com.example.healer.ieltsvocabulary.controller.UnitController;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Practice;
import com.example.healer.ieltsvocabulary.model.Unit;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;
import java.util.Random;

public class TestUnitFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Vocabulary> vocabularyLesson;
    private int lessonNumber;
    private String word;
    private String answer;
    private PracticeController pc;
    private int vocabularyID;
    private TextView question;
    Button word1, word2, word3, word4;
    TestUnitFragmentInterface mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_test_unit, container, false);

        question = (TextView) rootView.findViewById(R.id.txtQuestion);
        //question.setText("asfasfd safd asfdasdf a");
        TextView unitTitle = (TextView) rootView.findViewById(R.id.unitTitle);
        TextView lesson = (TextView) rootView.findViewById(R.id.lessonNumber);
        word1 = (Button) rootView.findViewById(R.id.test_compare_word1);
        word2 = (Button) rootView.findViewById(R.id.test_compare_word2);
        word3 = (Button) rootView.findViewById(R.id.test_compare_word3);
        word4 = (Button) rootView.findViewById(R.id.test_compare_word4);
        word1.setOnClickListener(this);
        word2.setOnClickListener(this);
        word3.setOnClickListener(this);
        word4.setOnClickListener(this);
        pc = new PracticeController(this.getContext());

        Bundle arguments = getArguments();
        if (arguments != null) {
            vocabularyLesson = (ArrayList<Vocabulary>) arguments.getSerializable("vocabularyLesson");
            lessonNumber = arguments.getInt("lesson");
            lesson.setText("Lesson " + String.valueOf(lessonNumber + 1));
            Unit unit = (new UnitController(this.getActivity())).getUnit(vocabularyLesson.get(0).getUnitId());
            unitTitle.setText(unit.getName());
            settingQuestion();
        }
        return rootView;
    }

    //Method set background and text color of other button except button is clicked.
    private void settingNormalButton(Button button1, Button button2, Button button3) {
        button1.setBackgroundResource(R.drawable.my_btn_bg);
        button1.setTextColor(getResources().getColor(R.color.test_dark_blue));
        button2.setBackgroundResource(R.drawable.my_btn_bg);
        button2.setTextColor(getResources().getColor(R.color.test_dark_blue));
        button3.setBackgroundResource(R.drawable.my_btn_bg);
        button3.setTextColor(getResources().getColor(R.color.test_dark_blue));
    }

    //Method set which button is the right answer, random the question
    private void settingQuestion() {

        int[] setWord = {0, 0, 0, 0, 0};
        int[] setPos = {0, 0, 0, 0};
        int[] setBtn = {0, 0, 0, 0};

        Random ans = new Random();
        int wordNum = ans.nextInt(vocabularyLesson.size());
        //answer = vocabularyLesson.get(wordNum).getMean();
        word = vocabularyLesson.get(wordNum).getWord();
        vocabularyID = vocabularyLesson.get(wordNum).getId();
        answer = word;

        Practice practice = pc.getPracticeById(vocabularyID);
        if (practice != null) {
            Log.d("idVocabulary", practice.getSentence());
            question.setText(practice.getSentence());
        }


        // Random position contain answer
        setWord[wordNum] = 1;
        Random p = new Random();
        int pos = p.nextInt(4);
        if (pos == 0) {
            word1.setText(word);
            setBtn[0] = 1;
        }
        if (pos == 1) {
            word2.setText(word);
            setBtn[1] = 1;
        }
        if (pos == 2) {
            word3.setText(word);
            setBtn[2] = 1;
        }
        if (pos == 3) {
            word4.setText(word);
            setBtn[3] = 1;
        }
        setPos[pos] = 1;

        // Random 3 position contain 3 answer
        for (int i = 0; i < 3; i++) {
            Random ques = new Random();
            int num = ques.nextInt(5);
            int p1 = ques.nextInt(4);
            while (setWord[num] == 1 || setPos[p1] == 1) {
                num = ques.nextInt(5);
                p1 = ques.nextInt(4);
            }
            String wordIncorrect = vocabularyLesson.get(num).getWord();
            setWord[num] = 1;
            setPos[p1] = 1;
            if (setBtn[p1] == 0 && p1 == 0) {
                word1.setText(wordIncorrect);
                setBtn[0] = 1;
            }
            if (setBtn[p1] == 0 && p1 == 1) {
                word2.setText(wordIncorrect);
                setBtn[1] = 1;
            }
            if (setBtn[p1] == 0 && p1 == 2) {
                word3.setText(wordIncorrect);
                setBtn[2] = 1;
            }
            if (setBtn[p1] == 0 && p1 == 3) {
                word4.setText(wordIncorrect);
                setBtn[3] = 1;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_compare_word1:
                if ((word1.getText().toString().toLowerCase()).equals(answer.toLowerCase())) {
                    //      Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();
                    word1.setBackgroundColor(Color.parseColor("#0288D1"));
                    word1.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word2, word3, word4);
                    mCallback.onReturnAnswerBlank(true, word1);
                } else {
                    word1.setBackgroundColor(Color.parseColor("#0288D1"));
                    word1.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word2, word3, word4);
                    mCallback.onReturnAnswerBlank(false, word1);
                }
                break;

            case R.id.test_compare_word2:
                if ((word2.getText().toString().toLowerCase()).equals(answer.toLowerCase())) {
                    //  Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();
                    word2.setBackgroundColor(Color.parseColor("#0288D1"));
                    word2.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word1, word3, word4);
                    mCallback.onReturnAnswerBlank(true, word2);
                } else {
                    word2.setBackgroundColor(Color.parseColor("#0288D1"));
                    word2.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word1, word3, word4);
                    mCallback.onReturnAnswerBlank(false, word2);
                }
                break;
            case R.id.test_compare_word3:
                if ((word3.getText().toString().toLowerCase()).equals(answer.toLowerCase())) {
                    //    Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();
                    word3.setBackgroundColor(Color.parseColor("#0288D1"));
                    word3.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word2, word1, word4);
                    mCallback.onReturnAnswerBlank(true, word2);
                } else {
                    word3.setBackgroundColor(Color.parseColor("#0288D1"));
                    word3.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word2, word1, word4);
                    mCallback.onReturnAnswerBlank(false, word3);
                }
                break;
            case R.id.test_compare_word4:
                if ((word4.getText().toString().toLowerCase()).equals(answer.toLowerCase())) {
                    //  Toast.makeText(getContext(),"1 TRUE!", Toast.LENGTH_LONG).show();
                    word4.setBackgroundColor(Color.parseColor("#0288D1"));
                    word4.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word2, word3, word1);
                    mCallback.onReturnAnswerBlank(true, word4);
                } else {
                    word4.setBackgroundColor(Color.parseColor("#0288D1"));
                    word4.setTextColor(getResources().getColor(R.color.white));
                    settingNormalButton(word2, word3, word1);
                    mCallback.onReturnAnswerBlank(false, word4);
                }
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
    }

    // Container Activity must implement this interface
    public interface TestUnitFragmentInterface {
        public boolean onReturnAnswerBlank(boolean answer, Button A);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        try {
            mCallback = (TestUnitFragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TestWordInterface");
        }
    }
}
