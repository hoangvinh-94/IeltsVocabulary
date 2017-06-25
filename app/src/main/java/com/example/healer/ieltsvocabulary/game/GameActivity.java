package com.example.healer.ieltsvocabulary.game;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healer.ieltsvocabulary.R;
import com.example.healer.ieltsvocabulary.controller.ImageController;
import com.example.healer.ieltsvocabulary.model.Vocabulary;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener,View.OnDragListener {
    ImageView[] PT;
    char []A;
    TextView []TV;
    private ArrayList<Vocabulary> vocabularyLesson;
    private ImageController im;
    private TableLayout layoutBlank;
    private TableLayout layoutResult;
    private ImageView image;
    private Vector v;
    private int score = 0;
    private LinearLayout layoutGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_game);
        im = new ImageController(this);
        v = new Vector();
        Intent intent = getIntent();
        vocabularyLesson = (ArrayList<Vocabulary>) intent.getBundleExtra("dataLesson").getSerializable("vocabularyLesson");
        int idUnit = intent.getBundleExtra("dataLesson").getInt("idUnit");
        layoutBlank = (TableLayout) findViewById(R.id.layoutBlank);
        layoutResult = (TableLayout) findViewById(R.id.layoutResult);
        Button btnplayGame = (Button) findViewById(R.id.btnPlay);
        layoutGame = (LinearLayout) findViewById(R.id.layout_start_game);
        final ImageView imageAnimation = (ImageView)findViewById(R.id.imageAnimation);
        image = (ImageView)findViewById(R.id.imageView);
        final AnimationDrawable animation =  (AnimationDrawable) imageAnimation.getDrawable();
        imageAnimation.post(new Runnable() {
            @Override
            public void run() {
                animation.start();
            }
        });
        btnplayGame.setOnClickListener(this);
        randomNewWord(vocabularyLesson,im);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,MyServices.class));
    }

    public void randomNewWord(ArrayList<Vocabulary> A, ImageController im){
        Random r = new Random();
        int k = r.nextInt(A.size());
        while(v.contains(k)){
            k = r.nextInt(A.size());
        }
        v.add(k);
        //image.setImageBitmap(im.loadImage(A.get(k).getImage()));
        resetLayout();
        createRow(A.get(k).getWord(),layoutBlank);
        createRow1(A.get(k).getWord(),layoutResult);

    }

    public void createRow (String str, TableLayout l){
        A = getArrayCharacter(str);
        int len = A.length;
        int len1 = 0;
        if(len > 6){
            len1 = 6;
        }
        else{
            len1 = len;
        }
        Log.d("size",String.valueOf(len));
        int count = 0;
        Vector vr = new Vector();
        Random rr = new Random();
        for(int i=0; i < A.length/6 + 1; i++){
            TableRow row = null;
            row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = count; j < len1; ){

                int k1 = rr.nextInt(A.length);
                if(!vr.contains(k1)) {
                    Log.d("k1",String.valueOf(k1));
                    LinearLayout LN = new LinearLayout(this);
                    TableRow.LayoutParams paramsRL = new TableRow.LayoutParams(0, 80);
                    paramsRL.weight = 1;
                    paramsRL.setMargins(5, 5, 5, 5);
                    LN.setLayoutParams(paramsRL);
                    LN.setBackgroundColor(Color.WHITE);

                    TextView txt = new TextView(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    params.setMargins(5,5,5,5);
                    txt.setLayoutParams(params);
                    txt.setGravity(Gravity.CENTER);
                    txt.setTextColor(Color.BLUE);
                    txt.setTextSize(25);
                    txt.setTypeface(Typeface.DEFAULT_BOLD);
                    txt.setText(String.valueOf(A[k1]));
                    vr.add(k1);
                    j++;
                    txt.setOnTouchListener(this);
                    LN.addView(txt);
                    row.addView(LN);
                }
            }
            l.addView(row);

            count = 6;
            len1 = len1 + 6;
            if(len1 > len){
                len1 = len;
            }
        }
    }

    public void createRow1 (String str, TableLayout l){
        A = getArrayCharacter(str);
        TV = new TextView[A.length];
        int len = A.length;
        int len1 = 0;
        if(len > 6){
            len1 = 6;
        }
        else{
            len1 = len;
        }
        int count = 0;
        for(int i=0; i < A.length/6 + 1; i++){
            TableRow row = null;
            row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = count; j < len1; j++){
                    TextView txt = new TextView(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(0, 80);
                    params.weight = 1;
                    params.setMargins(5, 5, 5, 5);
                    txt.setLayoutParams(params);
                    txt.setBackgroundResource(R.drawable.backotrong);
                    txt.setGravity(Gravity.CENTER);
                    txt.setTextSize(25);
                    txt.setTypeface(Typeface.DEFAULT_BOLD);
                    txt.setOnDragListener(this);
                    TV[j] = txt;
                    row.addView(txt);

            }
            l.addView(row);
            count = 6;
            len1 = len1 + 6;
            if(len1 > len){
                len1 = len;
            }
        }
    }

    public char [] getArrayCharacter(String str){
        char []A = str.toCharArray();
        return A;
    }

    public String getString(char []A){
        String str ="";
        for(int i = 0; i< A.length ; i++){
            str += A[i];
        }
        return str;
    }

    public void resetLayout(){
        layoutBlank.removeAllViews();
        layoutResult.removeAllViews();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPlay:
                layoutGame.setVisibility(View.INVISIBLE);
                Intent intent_services= new Intent(this,MyServices.class);
                Bundle bundle = new Bundle();
                bundle.putInt("PATH",R.raw.newstart);
                intent_services.putExtra("MUSIC", bundle);
                startService(intent_services);
                break;
            default: break;
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        switch(action) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup

                View v1 = (View) dragEvent.getLocalState();
                TextView dragged=(TextView)v1;
                TextView dropTarget = (TextView)view;
                dropTarget.setText(dragged.getText());
                dragged.setTextColor(Color.RED);
                MediaPlayer media = MediaPlayer.create(this, R.raw.matched);
                media.start();
                media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        mp.release();
                    }
                });
                int i=0;
                Log.d("TV",String.valueOf(TV.length));
                while(i < TV.length ){
                    if(!TV[i].getText().equals("")) {
                        i++;
                    }
                    else{
                        break;
                    }
                }
                if(i >= TV.length){
                    if(v.size() >= vocabularyLesson.size()){
                        Intent intent = new Intent(this,ScoreActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("vocabularys",vocabularyLesson);
                        bundle.putInt("score",score);
                        intent.putExtra("data",bundle);
                        startActivity(intent);
                    }
                    else {
                        String result = "";
                        for (int k = 0; k < TV.length; k++) {
                            result += TV[k].getText().toString().trim();
                        }
                        if (result.toLowerCase().equals(getString(A))) {
                            MediaPlayer mp = MediaPlayer.create(this, R.raw.votay);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    // TODO Auto-generated method stub
                                    mp.release();
                                }
                            });
                            score++;
                            randomNewWord(vocabularyLesson, im);
                        } else {
                            for (int j = 0; j < getString(A).length(); j++) {
                                if (!TV[j].getText().toString().toLowerCase().equals(String.valueOf(getString(A).charAt(j)))) {
                                    TV[j].setBackgroundResource(R.drawable.backotrongerror);
                                } else {
                                    TV[j].setBackgroundResource(R.drawable.backotrong);
                                }
                            }
                            CountDownTimer timer = new CountDownTimer(1000 * 3, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    randomNewWord(vocabularyLesson, im);
                                }
                            }.start();
                        }
                    }
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default: break;
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            ClipData data= ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder,view, 0);
            MediaPlayer media = MediaPlayer.create(this, R.raw.pickedup);
            media.start();

            media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.release();
                }
            });
            return true;
        }
        else{
            return false;
        }

    }
}
