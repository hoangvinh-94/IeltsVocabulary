package com.example.healer.ieltsvocabulary.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainFragment extends Fragment {


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
        Bundle bundle = getArguments();
        Vocabulary vocabulary = (Vocabulary) bundle.getSerializable("vocabulary");
        word.setText(vocabulary.getWord());
        return rootView;
    }

}
