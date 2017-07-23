package com.example.healer.ieltsvocabulary;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final ActionBar bar = getSupportActionBar();
        bar.hide();
        ImageView image = (ImageView) findViewById(R.id.imageWelcome);
        final AnimationDrawable animation =  (AnimationDrawable) image.getDrawable();
        image.post(new Runnable() {
            @Override
            public void run() {
                animation.start();
            }
        });

        CountDownTimer timer = new CountDownTimer(1000 * 8, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                animation.stop();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
