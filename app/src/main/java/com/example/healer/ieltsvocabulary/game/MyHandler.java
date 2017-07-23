package com.example.healer.ieltsvocabulary.game;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


public class MyHandler extends Handler {
	MediaPlayer myPlayer;
	public MyHandler(Looper looper){
		super(looper);
	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		int startId = msg.arg1;
		Bundle bundle =msg.getData();
		int path = bundle.getInt("PATH");
        Context context = (Context)msg.obj; //Lay context ra
        if(context!=null) {
            if(msg.arg2==1) {
                if(myPlayer != null){
                    myPlayer.stop();
                }
                else {
                    myPlayer = MediaPlayer.create(context, path);
                    myPlayer.setLooping(true); // Set looping
                    myPlayer.start();
                    myPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.release();
                        }
                    });
                }
            }
            else if(msg.arg2==2) {
                myPlayer.stop();
            }

        }
	}
	

}
