package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CutomerNotificationOrderDetails extends AppCompatActivity {

    private ImageView back_btn_orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_notification_order_details);

        back_btn_orderDetail =  findViewById(R.id.back_btn_customernotificationorderDetail);

        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CustomerNotification.class);
                startActivity(intent);
            }
        });

    }
}
