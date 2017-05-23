package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.adapter.MyDataBaseAdapter;
import com.example.healer.ieltsvocabulary.model.Lesson;
import com.example.healer.ieltsvocabulary.model.Unit;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class LessonFragment extends  Fragment {
	ArrayAdapter<String> lists = null;
	MyDataBaseAdapter myData = null;
	SQLiteDatabase myDataBase = null;
	int id;
	public static LessonFragment newInstance(int pos,int id) {

		Bundle args = new Bundle();
		args.putInt("pos",pos);
		args.putInt("id",id);
		LessonFragment fragment = new LessonFragment();
		fragment.setArguments(args);

		return fragment;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle BundlesavedInstanceState){
		View rootView = inflater.inflate(R.layout.category_lesson,container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.listLesson);
		myData = new MyDataBaseAdapter(this.getActivity());
		myData.open();
		myDataBase = myData.getMyDatabase();
		id = getArguments().getInt("id");
		int a = loadData();
		Log.d("a",String.valueOf(a));
		int count = 0;
		int j = 0;
		String[] arrStr = new String[a/5];
		for(int i=0; i < a; i++){
			count++;
			if(count == 5){
				arrStr[j] = "Lesson "+ (j+1);
				count = 0;
				j++;
			}
			else if(count == (a - (a/5)*(j+1)) && i >= ((a/5)*(j+1)) ){
				arrStr[j] = "Lesson "+ (j+1);
				count = 0;
				j++;
			}
		}
		lists = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,arrStr);
		listView.setAdapter(lists);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				StudyTypeDFragment studyType = StudyTypeDFragment.newInstance(arg2);
				studyType.show(getActivity().getFragmentManager(),"");
				
			}
			
		});
		return rootView;
	}
	public int loadData(){
		int a = 0;
		Cursor c = myDataBase.rawQuery("SELECT * FROM UNIT WHERE _id = '"+id+"'", null);
		c.moveToFirst();
		a = c.getInt(2);
		c.close();
		return a;
	}
	
	
	
	
	
}
