package com.example.healer.ieltsvocabulary.game;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;
import android.os.Message;

public class MyServices extends Service{
	Handler handler;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		HandlerThread thread = new HandlerThread("ThreadHandler",android.os.Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		Looper looper = thread.getLooper();
		handler = new MyHandler(looper);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//return super.onStartCommand(intent, flags, startId);
		if(intent==null)
            Log.d("intent","null");
        else {
            Log.d("intent", "not null");
            //Dong goi nhung thong tin can gui cho Handler xu li
            //Neu co duong dan file nhac thi dong goi trong msg
            //Can dong goi them Context de khoi tao Media Player
            Message msg = handler.obtainMessage();
            Bundle bundle = intent.getBundleExtra("MUSIC");
            msg.arg1 = startId;
            msg.setData(bundle);
            msg.obj = this; //dong goi Context de gui di
            msg.arg2 = 1; //1 là choi nhac, 2 là stop nhac
            handler.sendMessage(msg); //sau khi goi sendMessage, su kien handleMessage ben Handler se lay msg ra va xu li
        }
        return START_STICKY;

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Message msg = handler.obtainMessage();
		msg.obj = this; //dong goi Context de gui di
		msg.arg2 = 2; //1 là choi nhac, 2 là stop nhac
		handler.sendMessage(msg);
	}
	

}
