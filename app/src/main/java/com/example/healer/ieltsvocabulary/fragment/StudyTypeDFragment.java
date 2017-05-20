package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.LessonActivity;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class StudyTypeDFragment extends DialogFragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_typestudy, null);
		ImageButton imgStudy = (ImageButton) rootView.findViewById(R.id.imgStudy);
		ImageButton imgGame= (ImageButton) rootView.findViewById(R.id.imgGame);

		ImageButton imgTest = (ImageButton) rootView.findViewById(R.id.imgTest);
		ImageButton imgTest1 = (ImageButton) rootView.findViewById(R.id.imgTest1);
		imgGame.setOnClickListener(this);
		imgStudy.setOnClickListener(this);
		imgTest.setOnClickListener(this);
		imgTest1.setOnClickListener(this);
		return rootView;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.imgStudy:{
			Intent intent = new Intent(this.getActivity(),LessonActivity.class);
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
