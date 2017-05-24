package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.adapter.MyDataBaseAdapter;
import com.example.healer.ieltsvocabulary.adapter.NumOfLessonAdapter;
import com.example.healer.ieltsvocabulary.model.Lesson;
import com.example.healer.ieltsvocabulary.model.Unit;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

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
	NumOfLessonAdapter lists = null;
	MyDataBaseAdapter myData = null;
	SQLiteDatabase myDataBase = null;
	ArrayList<Vocabulary> list = null;
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
		list = new ArrayList<Vocabulary>();
		id = getArguments().getInt("id");
		int a = loadId();
		list = loadData();

		Log.d("a",String.valueOf(a));
		int count = 0;
		int j = 0;
		ArrayList<Vocabulary> vocabularies = new ArrayList<>();
		final ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		for(int i=0; i < a; i++){
			vocabularies.add(new Vocabulary(list.get(i).getWord()));
			count++;
			if(count == 5){
				lessons.add(new Lesson(j+1,"Lesson "+ (j+1),vocabularies));
				count = 0;
				j++;
				vocabularies.clear();
			}
			else if(count == (a - (a/5)*5) && i >= ((a/5)*5) ){
				lessons.add(new Lesson(j+1,"Lesson "+ (j+1),vocabularies));
				count = 0;
				j++;
				vocabularies.clear();
			}
		}
		lists = new NumOfLessonAdapter(this.getActivity(),R.layout.custom_numof_lesson,lessons);
		listView.setAdapter(lists);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				StudyTypeDFragment studyType = StudyTypeDFragment.newInstance(lessons.get(arg2).getLessonId(),id);
				studyType.show(getActivity().getFragmentManager(),"");
				
			}
			
		});
		return rootView;
	}
	public int loadId(){
		int a = 0;
		Cursor c = myDataBase.rawQuery("SELECT * FROM UNIT WHERE _id = '"+id+"'", null);
		c.moveToFirst();
		a = c.getInt(2);
		c.close();
		return a;
	}
	public ArrayList<Vocabulary> loadData(){
		ArrayList<Vocabulary>  vocabularies = new ArrayList<Vocabulary>();
		Cursor c = myDataBase.rawQuery("SELECT * FROM VOCABULARY WHERE unitId = '"+id+"'", null);
		c.moveToFirst();
		while(c.isAfterLast() == false){
			vocabularies.add(new Vocabulary(c.getString(1).toString().trim()));
			c.moveToNext();
		}
		c.close();
		return vocabularies;
	}
	
	
	
	
	
}
