package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Gallery extends AppCompatActivity {

    private TextView measurements, myorder;
    private ImageView customer_profile_icon, customer_settings_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

//        measurements                = findViewById(R.id.customer_measurements);
//        //customer_settings_icon       = findViewById(R.id.customer_settings_icon);
//        myorder = findViewById(R.id.customer_my_orders);

        //     INTENT TO MEASUREMENTS SCREEN
//        measurements.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Gallery.this,Measurements.class);
//                startActivity(intent);
//            }
//        });
//
//        myorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Gallery.this,Home_Customer.class);
//                startActivity(intent);
//            }
//        });
//
//        //     INTENT TO PROFILE SCREEN
//        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Gallery.this,Settings.class);
//                startActivity(intent);
//            }
//        });
    }
}
