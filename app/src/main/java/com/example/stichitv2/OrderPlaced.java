package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OrderPlaced extends AppCompatActivity {

    private ImageView back_btn_orderplaced;
    private Button editorder,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_orderplaced = findViewById(R.id.back_btn_orderPlaced);
        editorder = findViewById(R.id.orderplaced_editOrder);
        home = findViewById(R.id.orderplaced_home);

        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_orderplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Home_Customer.class);
                startActivity(intent);
            }
        });

        editorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SendOrder.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Home_Customer.class);
                startActivity(intent);
            }
        });
    }
}
