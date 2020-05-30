package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomerNotification extends AppCompatActivity {

    private TextView measurements, gallery, history, myorders;
    private ImageView customer_profile_icon, customer_settings_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_notification);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        //      DECLARATION OF  LAYOUT REFERENCE
        measurements                = findViewById(R.id.notificationcustomer_measurements);
        customer_settings_icon       = findViewById(R.id.notificationcustomer_settings_icon);
        gallery = findViewById(R.id.notificationcustomer_gallery);
        history = findViewById(R.id.notificationcustomer_history);
        myorders = findViewById(R.id.notificationcustomer_my_orders);

        //      GENERATING TAILORS LIST
        generateTailorsList(fragmentTransaction,fragmentManager);


        //     INTENT TO MEASUREMENTS SCREEN
        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNotification.this,Measurements.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNotification.this,Gallery.class);
                startActivity(intent);
            }
        });

        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNotification.this,Settings.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNotification.this,CustomerHistory.class);
                startActivity(intent);
            }
        });

        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNotification.this,Home_Customer.class);
                startActivity(intent);
            }
        });

    }

    //         Generates  tailors list and add it into the fragment
    public  void  generateTailorsList(FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ){


        ArrayList<String> sample_data =new ArrayList<>();
        sample_data.add("abc");
        sample_data.add("abc");
        sample_data.add("abc");
        sample_data.add("abc");
        sample_data.add("abc");

        for(int i= 0; i<sample_data.size(); i++){
            Customer_notification_list customer_notification_list  = new Customer_notification_list(sample_data,i);
            fragmentTransaction.add(R.id.customer_notification_fragment_container, customer_notification_list);
        }

        fragmentTransaction.commit();

    }

    private void removeAllFragments(FragmentManager fragmentManager) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


}



