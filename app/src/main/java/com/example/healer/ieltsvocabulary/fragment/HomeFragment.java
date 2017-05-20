package com.example.healer.ieltsvocabulary.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.example.healer.ieltsvocabulary.adapter.*;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.model.*;


public class HomeFragment extends Fragment{

	HomeAdapter homeAdapter = null;
	ArrayList<Unit> listUnit = null;
	GridView gridView = null;
	MyDataBaseAdapter myData = null;
	SQLiteDatabase myDataBase = null;

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle BundlesavedInstanceState){
		View rootView =inflater.inflate(R.layout.fragment_home,container,false);
		
		myData = new MyDataBaseAdapter(this.getActivity());
		myData.open();
		myDataBase = myData.getMyDatabase();
		
		gridView  = (GridView) rootView.findViewById(R.id.gridUnit);
		listUnit = new ArrayList<Unit>();
		//listUnit = loadData();
		listUnit.add(new Unit(0,"asdfas",123,"images/category1.jpg"));
		//listUnit.add(new Unit(1,"asdfasf",14,"images/category14.jpg"));
		//listUnit.add(new Unit(2,"asdfasf",14,"images/category15.jpg"));
		//listUnit.add(new Unit(3,"asdfasf",14,"images/category19.jpg"));
		//listUnit.add(new Unit(4,"asdfasf",14,"images/category10.jpg"));
		//listUnit.add(new Unit(5,"asdfasf",14,"images/category20.jpg"));

		homeAdapter = new HomeAdapter(getActivity(), listUnit);
		gridView.setAdapter(homeAdapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				HomeFragment hf = new HomeFragment();
				LessonFragment lf = new LessonFragment();
				hf.addChildFragment();
				Toast.makeText(getActivity(), "You clicked on item Topic", Toast.LENGTH_SHORT).show();
			}
		});
		return rootView;
	}

	public ArrayList<Unit> loadData(){
		ArrayList<Unit> arrs = new ArrayList<Unit>();
		Cursor c = myDataBase.rawQuery("SELECT * FROM UNIT", null);
		c.moveToFirst();
		String data = "";
		while(c.isAfterLast() == false){
			//arrs.add(new Unit(c.getInt(0),c.getString(1).toString().trim(),Integer.parseInt(c.getString(2)),c.getString(3).trim().toString()));
			arrs.add(new Unit(0,"asdfas",123,"images/category1.jpg"));
			c.moveToNext();
		}
		c.close();
		return arrs;
	}
		
	@SuppressLint({ "CommitTransaction", "NewApi" })
	public void addChildFragment()
	{
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		LessonFragment lf = new LessonFragment();
		ft.replace(R.id.fragment_container, lf);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	
	
}
