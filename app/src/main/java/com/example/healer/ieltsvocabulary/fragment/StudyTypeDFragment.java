package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.LessonActivity;
import com.example.healer.ieltsvocabulary.TestLessonActivity;
import com.example.healer.ieltsvocabulary.controller.ImageController;
import com.example.healer.ieltsvocabulary.game.GameActivity;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class StudyTypeDFragment extends DialogFragment implements OnClickListener {

	static ArrayList<Vocabulary> vocabularyLesson1;
	static int lessonNumber ;
	static  int max;

	public static StudyTypeDFragment newInstance(ArrayList<Vocabulary> vocabularyLesson, int lesson, int lessonMax) {
		vocabularyLesson1 = vocabularyLesson;
		StudyTypeDFragment fragment = new StudyTypeDFragment();
		lessonNumber = lesson;
		max = lessonMax;
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_typestudy, null);
		ImageController ic = new ImageController(this.getActivity());
		getDialog().setTitle("Your Selection");
		ImageButton imgStudy = (ImageButton) rootView.findViewById(R.id.imgStudy);
		imgStudy.setImageBitmap(ic.decodeFile("images/study-compressed.jpg"));
		ImageButton imgGame= (ImageButton) rootView.findViewById(R.id.imgGame);
		imgGame.setImageBitmap(ic.decodeFile("images/game-compressed.jpg"));
		ImageButton imgTest = (ImageButton) rootView.findViewById(R.id.imgTest);
		imgTest.setImageBitmap(ic.decodeFile("images/test-compressed.jpg"));
		imgGame.setOnClickListener(this);
		imgStudy.setOnClickListener(this);
		imgTest.setOnClickListener(this);
		return rootView;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putSerializable("vocabularyLesson", vocabularyLesson1);

		switch(v.getId()){
		case R.id.imgStudy:{
			Intent intent = new Intent(this.getActivity(),LessonActivity.class);
			bundle.putInt("lessonMax",max);
			bundle.putInt("lessonCurrent",lessonNumber);
			intent.putExtra("dataLesson",bundle);
			startActivity(intent);
			break;
		}
		case R.id.imgGame:{
			Intent intent = new Intent(this.getActivity(),GameActivity.class);
			intent.putExtra("dataLesson",bundle);
			startActivity(intent);
			break;
		}
		case R.id.imgTest:{
			Intent intent = new Intent(this.getActivity(),TestLessonActivity.class);
			bundle.putInt("lesson",lessonNumber);
			intent.putExtra("dataLesson",bundle);
			startActivity(intent);
			break;
		}
		default:break;
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		if (getDialog() != null && getDialog().isShowing()) {
			getDialog().dismiss();
		}
	}


}
