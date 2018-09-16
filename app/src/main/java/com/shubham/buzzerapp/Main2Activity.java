package com.shubham.buzzerapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main2Activity extends AppCompatActivity {

    TextView textView;
    ImageView img;
    MediaPlayer mediaPlayer;
    long duration = 960;
    int c=0;
    long onBackPressed;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       textView = (TextView) findViewById(R.id.name);
       img = (ImageView) findViewById(R.id.img);
        mediaPlayer = MediaPlayer.create(this,R.raw.buzz_audio);


        String s = getIntent().getStringExtra("e1");
        textView.setText(s);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (c==0){

                    ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(img,"rotation",0f,360f);
                    rotateAnim.setDuration(duration);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(rotateAnim);
                    animatorSet.start();

                    mediaPlayer.start();
                    send();

                    c++;
                    if(v==img){img.setImageResource(R.drawable.pressbuttonnext);}
                    CountDownTimer countDownTimer = new CountDownTimer(15000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                            //Toast.makeText(getApplicationContext(),"Next Question will show after 15 second",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFinish() {
                            c = 0;
                             img.setImageResource(R.drawable.pressbutton);
                        }
                    }.start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Your Response Already Send",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void send(){
        MessageSender messageSender = new MessageSender();
        messageSender.execute(textView.getText().toString());
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
