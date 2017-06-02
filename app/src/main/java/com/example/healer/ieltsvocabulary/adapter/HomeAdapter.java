package com.example.healer.ieltsvocabulary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.HomeActivity;
import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.fragment.LessonFragment;

import java.io.IOException;
import java.io.InputStream;

public class HomeAdapter extends CursorAdapter {

	private static final int VIEW_TYPE_COUNT = 1;
	private static final int VIEW_TYPE = 0;

	public static class ViewHolder {
		public final ImageButton img;

		public ViewHolder(View view) {
			img = (ImageButton) view.findViewById(R.id.img_unit);
			//img.setImageBitmap(decodeFile(unit.getAvatar()));
		}
	}
	// init HomeAdapter
	public HomeAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// Choose the layout type
		int viewType = getItemViewType(cursor.getPosition());
		int layoutId = -1;

		layoutId = R.layout.custom_home;

		View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);
		view.setTag(viewHolder);

		return view;
	}

	@Override
	public void bindView(View view, final Context context, final Cursor cursor) {

		ViewHolder viewHolder = (ViewHolder) view.getTag();
		int viewType = getItemViewType(cursor.getPosition());
		// Read date from cursor
		String avatarUri = cursor.getString(HomeActivity.COL_AVATAR_URI);
		Log.e("Avater Uri:", avatarUri);

		viewHolder.img.setImageBitmap(decodeFile(context, avatarUri));
		viewHolder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//Cursor cur = (Cursor) cursor.getPosition();
				LessonFragment lf = LessonFragment.newInstance(cursor.getPosition(),cursor.getInt(HomeActivity.COL_UNIT_ID));
				((Activity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,lf).addToBackStack(null).commit();
			}
		});
	}

	@Override
	public int getItemViewType(int position) {
		return VIEW_TYPE;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	private Bitmap decodeFile(Context myContext,String f){
		AssetManager mngr = myContext.getAssets();
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
