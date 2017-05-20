package com.example.healer.ieltsvocabulary.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.model.*;
import com.example.healer.ieltsvocabulary.fragment.*;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.R.attr.fragment;
import static com.example.healer.ieltsvocabulary.R.drawable.category1;


public class HomeAdapter extends BaseAdapter  {

	ArrayList<Unit> arr = null;
	Context context;

	
	
	public HomeAdapter(Context context, ArrayList<Unit> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.arr = list;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.custom_home,null);
		}
		Unit unit = arr.get(position);
		ImageButton img = (ImageButton) convertView.findViewById(R.id.img_unit);
		Log.d("pathImage",unit.getName());
		//img.setImageResource(R.drawable.category1);
		//img.setImageResource(unit.getAvatar1());
		//img.setImageURI(Uri.parse(unit.getAvatar()));
		AssetManager mngr = this.context.getAssets();
		InputStream is = null;
		try {
			is = mngr.open(unit.getAvatar());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(is != null){
			Bitmap bmImg = BitmapFactory.decodeStream(is);
			img.setImageBitmap(bmImg);
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arr.get(arg0);
	}


}
