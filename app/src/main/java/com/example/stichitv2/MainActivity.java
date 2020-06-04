package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.example.stichitv2.constant.CometChatConfig;

import screen.unified.CometChatUnified;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private ImageView logo;
    private String  appID   = CometChatConfig.appID; // Replace with your App ID
    private String  region  = CometChatConfig.region; // Replace with your App Region ("eu" or "us")
    private String  TAG     = "COMETCHAT_TEST";
    private String  authKey = CometChatConfig.authKey; // Replace with your App Auth Key



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo_icon);


        // Initialize CometChatPro

        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();
        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d(TAG, "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
            }
        });




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
