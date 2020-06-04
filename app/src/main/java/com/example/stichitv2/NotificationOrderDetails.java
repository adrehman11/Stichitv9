package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class NotificationOrderDetails extends AppCompatActivity {

    private ImageView back_btn_orderDetail;
    private Button accept, reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_order_details);


        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_orderDetail =  findViewById(R.id.back_btn_notificationorderDetail);
        accept = findViewById(R.id.notification_acceptOrder);
        reject = findViewById(R.id.notification_rejectOrder);


        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),NotificationTailor.class);
                startActivity(intent);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),NotificationTailor.class);
                startActivity(intent);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),NotificationTailor.class);
                startActivity(intent);
            }
        });


    }


}


