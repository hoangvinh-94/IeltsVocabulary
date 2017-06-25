package com.example.healer.ieltsvocabulary.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.ExampleController;
import com.example.healer.ieltsvocabulary.controller.ImageController;
import com.example.healer.ieltsvocabulary.controller.MeanController;
import com.example.healer.ieltsvocabulary.controller.SynonymousController;
import com.example.healer.ieltsvocabulary.controller.UnsynonymousController;
import com.example.healer.ieltsvocabulary.controller.VocabularyController;
import com.example.healer.ieltsvocabulary.controller.WordFormController;
import com.example.healer.ieltsvocabulary.controller.WordTypeController;
import com.example.healer.ieltsvocabulary.model.Example;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Synonymous;
import com.example.healer.ieltsvocabulary.model.Unsynonymous;
import com.example.healer.ieltsvocabulary.model.Vocabulary;
import com.example.healer.ieltsvocabulary.model.WordForm;
import com.example.healer.ieltsvocabulary.model.WordType;

import org.w3c.dom.Text;

import java.io.InputStream;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        // Inflate the layout for this fragment
        VocabularyController VC = new VocabularyController(this.getActivity());


        ImageButton record = (ImageButton) rootView.findViewById(R.id.recordWord);
        ImageButton listen = (ImageButton) rootView.findViewById(R.id.speakWord);
        ImageView image = (ImageView) rootView.findViewById(R.id.imageWord);
        TextView word = (TextView) rootView.findViewById(R.id.engWord);
        TextView mean = (TextView) rootView.findViewById(R.id.mean);
        TextView phonetic = (TextView) rootView.findViewById(R.id.phonetic);
        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        Bundle bundle = getArguments();
        final Vocabulary vocabulary = (Vocabulary) bundle.getSerializable("vocabulary");
        // get data by id
        ArrayList<WordType> wordTypes = (new WordTypeController(this.getContext()).getTypeByVocabularyId(vocabulary.getId()));
        ArrayList<Mean> means = (new MeanController(this.getContext()).getMeanById(vocabulary.getId()));
        ArrayList<Example> examples = (new ExampleController(this.getContext()).getExampleById(vocabulary.getId()));
        Synonymous synonymouses = (new SynonymousController(this.getContext()).getSynonymousById(vocabulary.getId()));
        Unsynonymous unsynonymouses = (new UnsynonymousController(this.getContext()).getUnsynonymousById(vocabulary.getId()));
        ArrayList<WordForm> wordforms = (new WordFormController(this.getContext()).getWordFormById(vocabulary.getId()));

        word.setText(vocabulary.getWord()+ " "+wordTypes.get(0).getSignature());
        phonetic.setText(vocabulary.getPhonetic());
        ImageController ic = new ImageController(this.getActivity());
        //image.setImageBitmap(ic.loadImage(vocabulary.getImage()));
        //image.setImageBitmap(ic.loadImg());
        //Log.d("image",vocabulary.getImage().toString());
        mean.setText(means.get(0).getMean());
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layout);

        // Display word type another

        for(int i=1; i<wordTypes.size();i++){
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText(vocabulary.getWord()+wordTypes.get(i).getSignature()+":");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.BLACK);
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);

            TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(means.get(i).getMean());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtContent.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            layout.addView(txtContent);
        }

        // Display example
        if(examples.size() > 0) {
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("Examples:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.BLACK);
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);

            for (int i = 0; i < examples.size(); i++) {
                TextView txtContent = new TextView(this.getActivity());
                txtContent.setText("\u25BA " + examples.get(i).getEngSentence());
                txtTitle.setId(View.generateViewId());
                txtContent.setTextColor(Color.BLACK);
                txtContent.setTextSize(18);
                txtContent.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                layout.addView(txtContent);
            }
        }
        //Display word synonyms
        if(!synonymouses.getWord().isEmpty()) {
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("Synonyms:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.BLACK);
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);


              TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(synonymouses.getWord());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtContent.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            layout.addView(txtContent);

           // scrollView.addView(layout);
        }

        //Display word unsynonyms
        if(unsynonymouses != null) {
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("Unsynonyms:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.BLACK);
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);


            TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(unsynonymouses.getWord());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtContent.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            layout.addView(txtContent);
        }
        if(wordforms.size() > 0){
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("WordForm:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.BLACK);
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);
            String result = coverArrayToString(wordforms);
            TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(result.toString());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtContent.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            layout.addView(txtContent);
        }

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInputStudy();
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }

    public String coverArrayToString(ArrayList<WordForm> A){
        String str ="";
        for(int i=0; i < A.size(); i++){
            if(i == A.size() - 1){
                if(A.get(i).getWord() != null) {
                    str += A.get(i).getWord();
                }
            }
            else{
                if(A.get(i).getWord() != null) {
                    str += A.get(i).getWord() + " , ";
                }
            }
        }
        return str;
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
