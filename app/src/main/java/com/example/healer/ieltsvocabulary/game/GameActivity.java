package com.example.healer.ieltsvocabulary.game;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.IntegerRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.app.ActionBar;
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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener,View.OnDragListener {
    private char []A;
    private TextView []TV;
    private ArrayList<Vocabulary> vocabularyLesson;
    private ImageController im;
    private TableLayout layoutBlank;
    private TableLayout layoutResult;
    private ImageView image;
    private Vector v;
    private int score = 0;
    private LinearLayout layoutGame;
    private TextToSpeech textToSpeech;
    private int resultSpeech;
    private  TextView []Dragged;
    private int drag;
    private  TextView []DrogTarget;
    AnimationDrawable animation;
    private int posRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_game);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        im = new ImageController(this);
        v = new Vector();
        Intent intent = getIntent();
        vocabularyLesson = (ArrayList<Vocabulary>) intent.getBundleExtra("dataLesson").getSerializable("vocabularyLesson");
        layoutBlank = (TableLayout) findViewById(R.id.layoutBlank);
        layoutResult = (TableLayout) findViewById(R.id.layoutResult);
        Button btnplayGame = (Button) findViewById(R.id.btnPlay);
        layoutGame = (LinearLayout) findViewById(R.id.layout_start_game);
        final ImageView imageAnimation = (ImageView)findViewById(R.id.imageAnimation);
        image = (ImageView)findViewById(R.id.imageShow);
        image.setOnClickListener(this);
        animation =  (AnimationDrawable) imageAnimation.getDrawable();
        imageAnimation.post(new Runnable() {
            @Override
            public void run() {
                animation.start();
            }
        });
        btnplayGame.setOnClickListener(this);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    resultSpeech = textToSpeech.setLanguage(Locale.US);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Your device cannot read this word!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,MyServices.class));
        animation.stop();
    }

    // Random key word in 5 words available
    public void randomNewWord(final ArrayList<Vocabulary> A, ImageController im){
        Random r = new Random();
        int k = r.nextInt(A.size()); // random position in A Array
        while(v.contains(k)){
            k = r.nextInt(A.size());
        }
        v.add(k);
        posRandom = k;
        final int finalK = k;
        CountDownTimer timer = new CountDownTimer(1000 * 2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // đọc từ được random
                textToSpeech.speak(A.get(finalK).getWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }.start();

        image.setImageBitmap(im.decodeFile(A.get(k).getImage()));
        resetLayout();
        // gọi hàm tạo hai layout
        createRow(A.get(k).getWord(),layoutBlank);
        createRow1(A.get(k).getWord(),layoutResult);
        drag = 0;

    }

    // Create blank lines to contain random result
    @SuppressLint("NewApi")
    public void createRow (String str, TableLayout l){
        A = getArrayCharacter(str);
        int len = A.length;
        Dragged = new TextView[len];
        int len1 = 0;
        if(len > 6){
            len1 = 6;
        }
        else{
            len1 = len;
        }
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
                    LinearLayout LN = new LinearLayout(this);
                    TableRow.LayoutParams paramsRL = new TableRow.LayoutParams(0, 80);
                    paramsRL.weight = 1;
                    paramsRL.setMargins(5, 5, 5, 5);
                    LN.setLayoutParams(paramsRL);
                    LN.setBackgroundResource(R.drawable.bgtextview);

                    TextView txt = new TextView(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    params.setMargins(5,5,5,5);
                    txt.setLayoutParams(params);
                    txt.setId(View.generateViewId());
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


    // Create blank lines to contain result
    @SuppressLint("NewApi")
    public void createRow1 (String str, TableLayout l){
        A = getArrayCharacter(str);
        TV = new TextView[A.length];
        DrogTarget = new TextView[A.length];
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
                    txt.setId(View.generateViewId());
                    txt.setBackgroundResource(R.drawable.backotrong);
                    txt.setGravity(Gravity.CENTER);
                    txt.setTextSize(25);
                    txt.setTextColor(Color.BLUE);
                    txt.setTypeface(Typeface.DEFAULT_BOLD);
                    txt.setOnDragListener(this);
                    txt.setOnClickListener(this);
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

    // get ArrayCharacter from String
    public char [] getArrayCharacter(String str){
        char []A = str.toCharArray();
        return A;
    }
    // get String from Array
    public String getString(char []A){
        String str ="";
        for(int i = 0; i< A.length ; i++){
            str += A[i];
        }
        return str;
    }

    // Reset all layout when next Word
    public void resetLayout(){
        layoutBlank.removeAllViews();
        layoutResult.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (view.getId()){
            case R.id.btnPlay:
                CountDownTimer timer = new CountDownTimer(1000 * 2, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        layoutGame.setVisibility(View.INVISIBLE);
                        Intent intent_services= new Intent(getApplicationContext(),MyServices.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("PATH",R.raw.newstart);
                        intent_services.putExtra("MUSIC", bundle);
                        startService(intent_services);
                        randomNewWord(vocabularyLesson,im);
                        animation.stop();
                    }
                }.start();


                break;
            case R.id.imageShow:
                textToSpeech.speak(vocabularyLesson.get(posRandom).getWord(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            default:
                // khi nhấn vào từ kéo bị sai sẽ trở về vị trí ban đầu.
                for(int i=0;i<TV.length;i++){
                    if( DrogTarget.length > 0){
                        int j = 0;
                       while(j < DrogTarget.length )
                       {
                           if(DrogTarget[j] != null){

                               if(id != DrogTarget[j].getId()){
                                   j++;
                               }
                               else {
                                   if(!DrogTarget[j].getText().equals("")) {
                                       Dragged[j].setEnabled(true);
                                       Dragged[j].setText(DrogTarget[j].getText());
                                       DrogTarget[j].setText("");
                                       drag = j;
                                       break;
                                   }
                                   else{
                                       break;
                                   }
                               }
                           }
                           else{
                               break;
                           }
                       }
                       break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        switch(action) {
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View v1 = (View) dragEvent.getLocalState();
                TextView dragged=(TextView)v1;
                TextView dropTarget = (TextView)view;

                // kiểm tra điều kiện có thể kéo từ vào
                if(dropTarget.getText().equals(null) || dropTarget.getText().equals("")){
                    dropTarget.setText(dragged.getText());

                    // gán giá trị drop, drap trước đó để undo
                    Dragged[drag] = dragged;
                    DrogTarget[drag] = dropTarget;
                    drag++;
                    dragged.setText("");
                    dragged.setEnabled(false);
                    int i=0;
                    while(i < TV.length ){
                        if(!TV[i].getText().equals("")) {
                            i++;
                        }
                        else{
                            break;
                        }
                    }
                    if(i >= TV.length){
                        String result = "";
                        for (int k = 0; k < TV.length; k++) {
                            result += TV[k].getText().toString().trim();
                        }
                        checkWord(result);
                        CountDownTimer timer = new CountDownTimer(1000 * 3, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                // nếu đã hết từ sẽ chuyên sang màn hình kết quả
                                if(v.size() >= vocabularyLesson.size()) {
                                    Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("vocabularys", vocabularyLesson);
                                    bundle.putInt("score", score);
                                    intent.putExtra("data", bundle);
                                    startActivity(intent);
                                }
                                else{
                                    // random từ mới khác
                                    randomNewWord(vocabularyLesson, im);
                                }
                            }
                        }.start();
                    }
                }
                break;
            default: break;
        }
        return true;
    }

    // Kiểm tra từ đã kéo đã chính xác không
    public void checkWord( String result){
        if (result.toLowerCase().equals(getString(A))) {
            for (int j = 0; j < getString(A).length(); j++) {
                TV[j].setBackgroundResource(R.drawable.backotrongcorrect);
            }
            MediaPlayer mp= MediaPlayer.create(this,R.raw.matched);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.release();
                }
            });
            score++;
        } else {
            for (int j = 0; j < getString(A).length(); j++) {
                if (!TV[j].getText().toString().toLowerCase().equals(String.valueOf(getString(A).charAt(j)))) {
                    TV[j].setBackgroundResource(R.drawable.backotrongerror);
                } else {
                    TV[j].setBackgroundResource(R.drawable.backotrongcorrect);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
