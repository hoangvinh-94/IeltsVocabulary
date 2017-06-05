package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.LessonActivity;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StudyTypeDFragment extends DialogFragment implements OnClickListener {

	public static StudyTypeDFragment newInstance(ArrayList<Vocabulary> vocabularyLesson, int idUnit) {
		Bundle args = new Bundle();
		//args.putInt("idLesson",idLesson);
		args.putParcelableArrayList("vocabularyLesson", (ArrayList<? extends Parcelable>) vocabularyLesson);
		args.putInt("idUnit",idUnit);
		StudyTypeDFragment fragment = new StudyTypeDFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_typestudy, null);
		getDialog().setTitle("Your Selection");
		ImageButton imgStudy = (ImageButton) rootView.findViewById(R.id.imgStudy);
		imgStudy.setImageBitmap(decodeFile("images/study.png"));
		ImageButton imgGame= (ImageButton) rootView.findViewById(R.id.imgGame);
		imgGame.setImageBitmap(decodeFile("images/game.png"));
		ImageButton imgTest = (ImageButton) rootView.findViewById(R.id.imgTest);
		imgTest.setImageBitmap(decodeFile("images/test.png"));
		ImageButton imgTest1 = (ImageButton) rootView.findViewById(R.id.imgTest1);
		imgTest1.setImageBitmap(decodeFile("images/category1.jpg"));
		imgGame.setOnClickListener(this);
		imgStudy.setOnClickListener(this);
		imgTest.setOnClickListener(this);
		imgTest1.setOnClickListener(this);
		return rootView;
	}
	private Bitmap decodeFile(String f){
		AssetManager mngr = this.getActivity().getAssets();
		InputStream is = null;
		try {
			is = mngr.open(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is,null,o);

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize=8;
		return BitmapFactory.decodeStream(is, null, o2);
	}
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.imgStudy:{
			Intent intent = new Intent(this.getActivity(),LessonActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("vocabularyLesson", getArguments().getParcelableArrayList("vocabularyLesson"));
			bundle.putInt("idUnit", getArguments().getInt("idUnit"));
			intent.putExtra("id",bundle);
			startActivity(intent);
			Toast.makeText(getActivity(), "You clicked on Study Button", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.imgGame:{
			Toast.makeText(getActivity(), "You clicked on Game Button", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.imgTest:{
			Toast.makeText(getActivity(), "You clicked on Test Button", Toast.LENGTH_SHORT).show();
			break;
		}
		case R.id.imgTest1:{
			Toast.makeText(getActivity(), "You clicked on Test1 Button", Toast.LENGTH_SHORT).show();
			break;
		}
		default:break;
		}
	}


}
