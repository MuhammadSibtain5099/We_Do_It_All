package com.awais.homeservices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity extends AppCompatActivity {

     ImageView logo;
     TextView txtTitle;
     Animation top,bottom;
    private final Handler waitHandler = new Handler();
    private final Runnable waitCallback = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView( R.layout.activity_splash);

     //Amination
        top = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);
          logo =findViewById(R.id.imglogo);
          txtTitle =findViewById(R.id.txttitle);
          logo.setAnimation(top);
          txtTitle.setAnimation(bottom);
        //Fake wait 2s to simulate some initialization on cold start (never do this in production!)
        waitHandler.postDelayed(waitCallback, 5000);
    }

    @Override
    protected void onDestroy() {
        waitHandler.removeCallbacks(waitCallback);
        super.onDestroy();
    }
}
