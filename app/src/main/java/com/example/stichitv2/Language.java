package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Language extends AppCompatActivity {

    private ImageView back_btn_languge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        back_btn_languge = findViewById(R.id.back_btn_historyorderDetail);


        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_languge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Settings.class);
                startActivity(intent);
            }
        });
    }
}
