package com.example.healer.ieltsvocabulary.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
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
import com.example.healer.ieltsvocabulary.controller.ContextController;
import com.example.healer.ieltsvocabulary.controller.ExampleController;
import com.example.healer.ieltsvocabulary.controller.ImageController;
import com.example.healer.ieltsvocabulary.controller.MeanController;
import com.example.healer.ieltsvocabulary.controller.SynonymousController;
import com.example.healer.ieltsvocabulary.controller.UnsynonymousController;
import com.example.healer.ieltsvocabulary.controller.VocabularyController;
import com.example.healer.ieltsvocabulary.controller.WordFormController;
import com.example.healer.ieltsvocabulary.controller.WordTypeController;
import com.example.healer.ieltsvocabulary.model.ContextVocabulary;
import com.example.healer.ieltsvocabulary.model.Example;
import com.example.healer.ieltsvocabulary.model.Mean;
import com.example.healer.ieltsvocabulary.model.Synonymous;
import com.example.healer.ieltsvocabulary.model.Unsynonymous;
import com.example.healer.ieltsvocabulary.model.Vocabulary;
import com.example.healer.ieltsvocabulary.model.WordForm;
import com.example.healer.ieltsvocabulary.model.WordType;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private int pos;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech textToSpeech;
    int resultSpeech;
    private String keyword = null;

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
        int position = bundle.getInt("pos");
        // get data by id
        ArrayList<WordType> wordTypes = (new WordTypeController(this.getContext()).getTypeByVocabularyId(vocabulary.getId()));
        ArrayList<Mean> means = (new MeanController(this.getContext()).getMeanById(vocabulary.getId()));
        ArrayList<Mean> meanContexts = (new MeanController(this.getContext()).getMeanInContextById(vocabulary.getId()));
        ArrayList<Example> examples = (new ExampleController(this.getContext()).getExampleById(vocabulary.getId()));
        Synonymous synonymouses = (new SynonymousController(this.getContext()).getSynonymousById(vocabulary.getId()));
        Unsynonymous unsynonymouses = (new UnsynonymousController(this.getContext()).getUnsynonymousById(vocabulary.getId()));
        ArrayList<WordForm> wordforms = (new WordFormController(this.getContext()).getWordFormById(vocabulary.getId()));
        ArrayList<ContextVocabulary> contexts = (new ContextController(this.getContext())).getContextById(vocabulary.getId());

        keyword = vocabulary.getWord();
        if(wordTypes.size() > 0){
            word.setText(vocabulary.getWord()+ " "+wordTypes.get(0).getSignature());
        }
        Log.d("keyword",keyword);
        phonetic.setText(vocabulary.getPhonetic());
        ImageController ic = new ImageController(this.getActivity());
        image.setImageBitmap(ic.decodeFile(vocabulary.getImage()));
        Log.d("image",vocabulary.getImage());
        if(means.size() > 0) {
            String s = "";
            for (int i = 0; i < means.size(); i++) {
                s += "\u25BA " +means.get(i).getMean()+"\n";
            }
            mean.setText(s);

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


        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layout);

        // Display word type another
        if(wordTypes.size() > 0) {
            for (int i = 1; i < wordTypes.size(); i++) {
                TextView txtTitle = new TextView(this.getActivity());
                txtTitle.setText(vocabulary.getWord() + wordTypes.get(i).getSignature() + ":");
                txtTitle.setId(View.generateViewId());
                txtTitle.setTextColor(Color.parseColor("#1E90FF"));
                txtTitle.setTextSize(20);
                txtTitle.setTypeface(null, Typeface.BOLD);
                txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.addView(txtTitle);

                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView txtContent = new TextView(this.getActivity());
                txtContent.setText(means.get(i).getMean());
                txtTitle.setId(View.generateViewId());
                txtContent.setTextColor(Color.BLACK);
                txtContent.setTextSize(18);
                txtParams.setMargins(30,0,0,0);
                txtContent.setLayoutParams(txtParams);
                layout.addView(txtContent);
            }
        }

        // Display example
        if(examples.size() > 0) {
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("Examples:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.parseColor("#1E90FF"));
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);
            for (int i = 0; i < examples.size(); i++) {
                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView txtContent = new TextView(this.getActivity());
                txtContent.setText("\u25BA " + examples.get(i).getEngSentence());
                txtTitle.setId(View.generateViewId());
                txtContent.setTextColor(Color.BLACK);
                txtContent.setTextSize(18);
                txtParams.setMargins(30,0,0,0);
                txtContent.setLayoutParams(txtParams);
                layout.addView(txtContent);
            }
        }

        //Display word synonyms
        if(synonymouses != null) {
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("Synonyms:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.parseColor("#1E90FF"));
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);

            LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(synonymouses.getWord());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtParams.setMargins(30,0,0,0);
            txtContent.setLayoutParams(txtParams);

            layout.addView(txtContent);

        }

        //Display word unsynonyms
        if(unsynonymouses != null) {
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("Unsynonyms:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.parseColor("#1E90FF"));
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);

            LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(unsynonymouses.getWord());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtParams.setMargins(30,0,0,0);
            txtContent.setLayoutParams(txtParams);

            layout.addView(txtContent);
        }

        // Display WordForm
        if(wordforms.size() > 0){
            TextView txtTitle = new TextView(this.getActivity());
            txtTitle.setText("WordForm:");
            txtTitle.setId(View.generateViewId());
            txtTitle.setTextColor(Color.parseColor("#1E90FF"));;
            txtTitle.setTextSize(20);
            txtTitle.setTypeface(null, Typeface.BOLD);
            txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(txtTitle);

            LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            String result = coverArrayToString(wordforms);
            TextView txtContent = new TextView(this.getActivity());
            txtContent.setText(result.toString());
            txtTitle.setId(View.generateViewId());
            txtContent.setTextColor(Color.BLACK);
            txtContent.setTextSize(18);
            txtParams.setMargins(30,0,0,0);
            txtContent.setLayoutParams(txtParams);

            layout.addView(txtContent);
        }

        // Display mean in another context
        if(contexts.size() > 0){
            for(int i=0; i<contexts.size();i++){
                TextView txtTitle = new TextView(this.getActivity());
                txtTitle.setText("Trong "+ contexts.get(i).getName()+":");
                txtTitle.setId(View.generateViewId());
                txtTitle.setTextColor(Color.parseColor("#1E90FF"));
                txtTitle.setTextSize(20);
                txtTitle.setTypeface(null, Typeface.BOLD);
                txtTitle.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.addView(txtTitle);

                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView txtContent = new TextView(this.getActivity());
                txtContent.setText(meanContexts.get(i).getMean());
                txtTitle.setId(View.generateViewId());
                txtContent.setTextColor(Color.BLACK);
                txtContent.setTextSize(18);
                txtParams.setMargins(30,0,0,0);
                txtContent.setLayoutParams(txtParams);

                layout.addView(txtContent);
            }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data  ) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getActivity());
                    View parentView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_record_text,null);
                    TextView txtResult = (TextView) parentView.findViewById(R.id.txtResultHear);
                    ImageView imageCheck = (ImageView) parentView.findViewById(R.id.imageCheck);
                    txtResult.setText(result.get(0).trim().toString().toLowerCase());
                    if(txtResult.getText().toString().trim().toLowerCase().equals(keyword.toString().trim().toLowerCase())){
                        imageCheck.setImageResource(R.drawable.correct2);
                    }
                    else{
                        imageCheck.setImageResource(R.drawable.error2);
                    }
                    bottomSheetDialog.setContentView(parentView);
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
                    bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()));
                    bottomSheetDialog.show();
                }
                break;
            }

        }

    }

    // sử dụng google api để nhận giọng nói
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeech.stop();
    }

}
