package com.example.healer.ieltsvocabulary.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.healer.ieltsvocabulary.HomeActivity;
import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.fragment.LessonFragment;
import com.example.healer.ieltsvocabulary.model.*;

import android.app.Activity;;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;


public class HomeAdapter extends ArrayAdapter<Unit> {

	ArrayList<Unit> arr = null;
	Activity context;
	int layoutId;



	public HomeAdapter(Activity context, int layoutId, ArrayList<Unit> list){
		super(context,layoutId,list);
		this.context = context;
		this.layoutId = layoutId;
		this.arr = list;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(layoutId, null);
		}
		final Unit unit = arr.get(position);
		ImageButton img = (ImageButton) convertView.findViewById(R.id.img_unit);
		img.setImageBitmap(decodeFile(unit.getAvatar()));
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LessonFragment lf = LessonFragment.newInstance(position,unit.getId());
				context.getFragmentManager().beginTransaction().replace(R.id.fragment_container,lf).addToBackStack(null).commit();

				//Toast.makeText(context,"asdfas",Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	// Decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(String f){
		AssetManager mngr = this.context.getAssets();
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


}
