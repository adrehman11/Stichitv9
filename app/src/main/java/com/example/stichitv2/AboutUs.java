package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {

    private ImageView back_btn_aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_aboutus =  findViewById(R.id.back_btn_aboutphone);

        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Settings.class);
                startActivity(intent);
            }
        });

    }
}
