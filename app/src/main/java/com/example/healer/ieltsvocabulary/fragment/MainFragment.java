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
import android.speech.tts.TextToSpeech;
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
    TextToSpeech textToSpeech;
    int resultSpeech;
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
        image.setImageBitmap(ic.getImage("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"));
        //Log.d("image",vocabulary.getImage().toString());
        mean.setText(means.get(0).getMean());


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
                textToSpeech.speak(vocabulary.getWord(), TextToSpeech.QUEUE_FLUSH, null);
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
