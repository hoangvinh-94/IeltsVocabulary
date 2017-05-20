package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LessonFragment extends  Fragment {
	ArrayAdapter<String> lists = null;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle BundlesavedInstanceState){
		View rootView = inflater.inflate(R.layout.category_lesson,container, false);
		ListView listView = (ListView) rootView.findViewById(R.id.listLesson);
		String[] arrStr = {"Lesson 1","Lesson 2","Lesson 3","Lesson 4"};
		lists = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,arrStr);
		listView.setAdapter(lists);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				StudyTypeDFragment studyTypeDFragment = new StudyTypeDFragment();
				studyTypeDFragment.show(getFragmentManager(),"");
				
			}
			
		});
		return rootView;
	}
	
	
	
	
	
}
