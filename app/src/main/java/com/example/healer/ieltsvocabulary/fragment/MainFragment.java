package com.example.healer.ieltsvocabulary.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private int pos;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        ImageButton record = (ImageButton) rootView.findViewById(R.id.recordWord);
        ImageButton listen = (ImageButton) rootView.findViewById(R.id.speakWord);
        TextView word = (TextView) rootView.findViewById(R.id.engWord);
        TextView mean = (TextView) rootView.findViewById(R.id.mean);
        TextView phonetic = (TextView) rootView.findViewById(R.id.phonetic);

        Bundle bundle = getArguments();
        final Vocabulary vocabulary = (Vocabulary) bundle.getSerializable("vocabulary");
        word.setText(vocabulary.getWord());
        String s = new String(vocabulary.getPhonetic());
        phonetic.setText("/"+s+"/");
        s = new String(vocabulary.getDetail());
        mean.setText(s);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInputStudy();
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vocabulary.getSound() == ""){
                    Toast.makeText(getActivity().getApplicationContext(),"No found file sound",Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
        return rootView;
    }


    private void promptSpeechInputStudy() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.US);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now ...");
        try {
            this.getActivity().startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity().getApplicationContext(),"Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityResultStudy(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data  ) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //if(result.get(0).trim().toString().toLowerCase().equals(getItem(pos).getWord().trim().toString().toLowerCase())){
                       // Intent intent = new Intent(getActivity(), MyService.class);
                        //intent.putExtra("pathSound","raw/matched");
                       // getActivity().startService(intent);
                    //}
                   // else{
                   //     Toast.makeText(getActivity().getApplication(),result.get(0).toString(),Toast.LENGTH_SHORT).show();
                   // }
                }
                break;
            }

        }
    }


}
