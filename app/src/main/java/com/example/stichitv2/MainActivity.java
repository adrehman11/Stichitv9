package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo_icon);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent   =   new Intent(MainActivity.this,SignIn.class);
                ActivityOptions  options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, logo,"logo");
                startActivity(intent,options.toBundle());

            }
        },SPLASH_TIME_OUT);

    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
