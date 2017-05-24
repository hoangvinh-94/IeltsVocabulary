package com.example.healer.ieltsvocabulary.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.fragment.LessonFragment;
import com.example.healer.ieltsvocabulary.model.Lesson;
import com.example.healer.ieltsvocabulary.model.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Healer on 24-May-17.
 */

public class NumOfLessonAdapter  extends ArrayAdapter<Lesson> {
    ArrayList<Lesson> arr = null;
    Activity context;
    int layoutId;
    public NumOfLessonAdapter(Activity context, int layoutId, ArrayList<Lesson> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }
        final Lesson lesson = arr.get(position);
        TextView txtLesson = (TextView)convertView.findViewById(R.id.txtLesson);
        txtLesson.setText(lesson.getNameLesson());
        return convertView;
    }
}
