package com.example.healer.ieltsvocabulary.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TestListeningFragment extends Fragment {
    ArrayList<Vocabulary> vocabularyLesson;
    String word;
    EditText resultEditText;
    ImageButton listenBtn;
    String answer;
    TextToSpeech textToSpeech;
    int resultSpeech;
    public boolean result = false;
    TestListeningInterface mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_test_listening, container, false);
        word = "";
        resultEditText = (EditText)rootView.findViewById(R.id.test_listening_edit_text);

        listenBtn = (ImageButton) rootView.findViewById(R.id.tracnghiem_word_Btn);
        Bundle arguments = getArguments();
        if (arguments != null) {
            vocabularyLesson = (ArrayList<Vocabulary>) arguments.getSerializable("vocabularyLesson");
            settingQuestion();
        } else {
            return rootView;
        }


        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    resultSpeech = textToSpeech.setLanguage(Locale.US);
                } else {
                    Toast.makeText(getContext(), "Your device cannot read this word!", Toast.LENGTH_SHORT).show();
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
        //Handle when user changed text in edit text to check answer
        resultEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(resultEditText.getText().toString().toLowerCase().equals(word.toLowerCase())){
                    Toast.makeText(getContext(), resultEditText.getText(), Toast.LENGTH_LONG).show();
                    mCallback.onReturnAnswerListening(true);
                }
                else {
                    mCallback.onReturnAnswerListening(false);
                }
            }
        });
        return rootView;
    }


    //Method set which button is the right answer, random the question
    private void settingQuestion() {
        Random ans = new Random();
        int wordNum = ans.nextInt(5);
        //answer = vocabularyLesson.get(wordNum).getMean();
        word = vocabularyLesson.get(wordNum).getWord();


    }

    //Do text to voice
    public void doTextToVoiceTest(View v) {
        switch (v.getId()) {
            case R.id.tracnghiem_word_Btn:
                if (resultSpeech == TextToSpeech.LANG_NOT_SUPPORTED || resultSpeech == TextToSpeech.LANG_MISSING_DATA) {
                    Toast.makeText(getContext(),
                            "You device is not support feature!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), word, Toast.LENGTH_LONG).show();
                    textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            default:
                Toast.makeText(getContext(),
                        "Error! You device cant speak this word!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //Stop and shutdown text to speech
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    //    // Container Activity must implement this interface
    public interface TestListeningInterface {
        public boolean onReturnAnswerListening(boolean answer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TestListeningInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TestWordInterface");
        }
    }
}