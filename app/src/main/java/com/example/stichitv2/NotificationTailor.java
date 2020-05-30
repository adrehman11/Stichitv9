package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class NotificationTailor extends AppCompatActivity {

    private ImageView tailor_profile_btn;
    private TextView orders, gallery, history, notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_tailor);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //      GENERATING CUSTOMERS LIST
        generateCustomersList(fragmentTransaction,fragmentManager);

        tailor_profile_btn       = findViewById(R.id.tailor_profile_btn);
        orders = findViewById(R.id.notification_my_orders);
        gallery = findViewById(R.id.notification_gallery);
        history = findViewById(R.id.notification_history);
        notification = findViewById(R.id.notification_notification);

        //     INTENT TO PROFILE SCREEN
        tailor_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationTailor.this,Profile.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationTailor.this,Home_Tailor.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationTailor.this,Home_Tailor.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationTailor.this, TailorHistory.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationTailor.this,NotificationTailor.class);
                startActivity(intent);
            }
        });

        // CALENDAR VIEW SETTINGS START

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .showBottomText(false)
                .formatTopText("EEE")
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
            }
        });

        // CALENDAR VIEW SETTINGS END

    }

    //         Generates  tailors list and add it into the fragment
    public  void  generateCustomersList(FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ){


        ArrayList<String> sample_data =new ArrayList<>();
        sample_data.add("abc");
        sample_data.add("abc");
        sample_data.add("abc");
        sample_data.add("abc");
        sample_data.add("abc");

        for(int i= 0; i<sample_data.size(); i++){
            Notification_list_fragment notification_list_fragment= new Notification_list_fragment(sample_data,i);
            fragmentTransaction.add(R.id.tailor_notification_fragment_container,notification_list_fragment);
        }

        fragmentTransaction.commit();
    }
}
