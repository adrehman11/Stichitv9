package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpandSupport extends AppCompatActivity {

    private ImageView back_btn_helpandsupport;
    private TextView reportproblem;
    private Button reportproblem_btn, gotanswer_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpand_support);

        back_btn_helpandsupport = findViewById(R.id.back_btn_Faqs);
        reportproblem = findViewById(R.id.reportprobleminFAQs);
        reportproblem_btn = findViewById(R.id.reportproblmbuttononfaq);
        gotanswer_btn = findViewById(R.id.gotanswerbuttonfaq);

        back_btn_helpandsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Settings.class);
                startActivity(intent);
            }
        });

        reportproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ReportProblem.class);
                startActivity(intent);
            }
        });

        reportproblem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ReportProblem.class);
                startActivity(intent);
            }
        });

        gotanswer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Settings.class);
                startActivity(intent);
            }
        });
    }
}
