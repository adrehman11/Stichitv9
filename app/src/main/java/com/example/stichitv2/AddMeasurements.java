package com.example.stichitv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddMeasurements extends AppCompatActivity {

    private Button savemeasurements, skipmeasuremnet;
    private ImageView back_btn_addmeasurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_measurements);

        savemeasurements = findViewById(R.id.addMeasurements_saveButton);
        skipmeasuremnet = findViewById(R.id.addMeasurements_skipbtn);
        back_btn_addmeasurements = findViewById(R.id.back_btn_addMeasurements);


        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_addmeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), SignUp.class);
                startActivity(intent);
            }
        });

        savemeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Home_Customer.class);
                startActivity(intent);
            }
        });

        skipmeasuremnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Home_Customer.class);
                startActivity(intent);
            }
        });
    }
}
