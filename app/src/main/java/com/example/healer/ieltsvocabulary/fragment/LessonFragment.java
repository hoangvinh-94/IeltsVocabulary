package com.example.healer.ieltsvocabulary.fragment;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.VocabularyController;
import com.example.healer.ieltsvocabulary.adapter.NumOfLessonAdapter;
import com.example.healer.ieltsvocabulary.model.Lesson;
import com.example.healer.ieltsvocabulary.model.SaveState;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class LessonFragment extends  Fragment implements View.OnClickListener {
	static int id;
	private int idUnit;
	private ListView listView;
	private int a = 5;
	private ArrayList<Vocabulary> list;
	private static String NUMBER_OF_WORD = "number";
	private static String DATA_SAVED = "data saved";
	private static String ID_UNIT = "id";
	private static String LESSON_FINISHED = "studiedWord";
	private static String M = "m";

	private Button btnAddLesson;
	private ArrayList<SaveState> DataSaves = null;
	int m = 0;
	private int[] ID = new int[20];
	private int[] Number = new int[20];
	public static boolean studiedWord = false;
	// create New LessonFragment with possition and id Unit
	public static LessonFragment newInstance(int idUnit) {
		Bundle args = new Bundle();
		id = idUnit;
		LessonFragment fragment = new LessonFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle BundlesavedInstanceState){
		View rootView = inflater.inflate(R.layout.category_lesson,container, false);
		final int NUMBER_MAX_UNIT = 20;
		final int DEFAULT_VALUE = 0;

		listView = (ListView) rootView.findViewById(R.id.listLesson);
		btnAddLesson = (Button) rootView.findViewById(R.id.addLesson);
		btnAddLesson.setOnClickListener(this);
		VocabularyController VC = new VocabularyController(this.getActivity());
		list = new ArrayList<Vocabulary>();
		list = VC.loadDataByUnitId(id);
		SharedPreferences loadData = this.getActivity().getSharedPreferences(DATA_SAVED,DEFAULT_VALUE);

		if(loadData.contains(NUMBER_OF_WORD) || loadData.contains(ID_UNIT)){
			loadState();
			int p = 0;
			while (p < NUMBER_MAX_UNIT && ID[p] != id ){;
				p++;
			}
			if(p < 20){
				a = Number[p];
			}
			else{

			}
		}
		loadLesson(a,list);
		return rootView;
	}

	// Lưu trạng thái bài học hiện tại của người học
	public void saveState(){
		// Luu trang thai
		SharedPreferences saveData = this.getActivity().getSharedPreferences(DATA_SAVED, MODE_PRIVATE);
		SharedPreferences.Editor editor = saveData.edit();
		int posUnit = id;
		--posUnit;
		ID[posUnit] = id;
		Number[posUnit] = a;
		editor.putString(ID_UNIT, Arrays.toString(ID));
		editor.putString(NUMBER_OF_WORD, Arrays.toString(Number));
		editor.putBoolean(LESSON_FINISHED,studiedWord);
		editor.commit();
	}

	// Load lại số bài học của người học đã thưc hiện trước đó.
	public void loadState(){
		SharedPreferences loadData = this.getActivity().getSharedPreferences(DATA_SAVED,0);
		studiedWord = loadData.getBoolean(LESSON_FINISHED,false);
		String IDUNIT = loadData.getString(ID_UNIT, null);
		String NUMBERWORD = loadData.getString(NUMBER_OF_WORD, null);
		//m = loadData.getInt(M,0);
		if(IDUNIT!=null){
			String []split=IDUNIT.substring(1, IDUNIT.length()-1).split(", ");
			for(int j=0;j<split.length;j++){
				ID[j]=Integer.parseInt(split[j]);
			}
		}
		if(Number!=null){
			String []split=NUMBERWORD.substring(1, NUMBERWORD.length()-1).split(", ");
			for(int j=0;j<split.length;j++){
				Number[j]=Integer.parseInt(split[j]);
			}
		}
	}

	// load lessons bắt đầu từ lesson 1
	public void loadLesson(int a, ArrayList<Vocabulary> list){
		if(list.size() > a){
			Toast.makeText(getActivity(), "New data had to update!", Toast.LENGTH_SHORT).show();
		}
		NumOfLessonAdapter lists = null;
			int count = 0;
			int j = 0;
			final ArrayList<Lesson> lessons = new ArrayList<Lesson>();
			ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();
			String []lessonTitle = new String[5];
			for (int i = 0; i < a; i++) {
				vocabularies.add(list.get(i));
				lessonTitle[count] = list.get(i).getWord();
				count++;
				if (count == 5) {
					lessons.add(new Lesson(j,"Lesson "+ (j + 1)+"\n("+ coverArrayToString(lessonTitle)+")", vocabularies));
					count = 0;
					j++;
					vocabularies = new ArrayList<Vocabulary>();
					lessonTitle = new String[5];
				} else if (count == (a - (a / 5) * 5) && i >= ((a / 5) * 5)) {
					lessons.add(new Lesson(j,"Lesson "+ (j + 1)+"\n("+ coverArrayToString(lessonTitle)+")", vocabularies));
					count = 0;
					j++;
					vocabularies = new ArrayList<Vocabulary>();
					lessonTitle = new String[5];
				}
			}

			lists = new NumOfLessonAdapter(this.getActivity(), R.layout.custom_numof_lesson, lessons);
			listView.setAdapter(lists);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
										long arg3) {
					// TODO Auto-generated method stub

					StudyTypeDFragment studyType = StudyTypeDFragment.newInstance(lessons.get(position).getVocabularies(),position,lessons.size());
					studyType.show(getActivity().getFragmentManager(), "");
				}

			});

	}

	// Chuyển mảng char sang chuỗi
	public String coverArrayToString(String[] A){
		String str ="";
		for(int i=0; i < A.length; i++){
			if(i == A.length - 1){
				if(A[i] != null) {
					str += A[i];
				}
			}
			else{
				if(A[i] != null) {
					str += A[i] + " / ";
				}
			}
		}
		return str;
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.addLesson:
				if(list.size() > a) {
					if((list.size() - a) > 5 ){
						a += 5;
					}
					else{
						a = list.size();
					}
					loadLesson(a,list);
					studiedWord = false;
					btnAddLesson.setVisibility(View.INVISIBLE);
				}
				else{
					Toast.makeText(getActivity(),"New data hadn't to Update!",Toast.LENGTH_SHORT).show();
				}
				break;
			default:break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if(list.size() > a && studiedWord){
			btnAddLesson.setVisibility(View.VISIBLE);
		}
	}
}
