package com.example.stichitv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ARView extends AppCompatActivity {


    private TextView measurements, gallery, notification, history, neworder, pending,home, tailornearme;
    private ImageView customer_profile_icon, customer_settings_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_view);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        //      DECLARATION OF  LAYOUT REFERENCE
        measurements                = findViewById(R.id.customer_ARView_measurements);
        customer_settings_icon       = findViewById(R.id.customer_settings_icon_arView);
        gallery = findViewById(R.id.customer_ARView_gallery);
        notification = findViewById(R.id.customer_ARView_notification);
        history = findViewById(R.id.customer_ARView_history);
        neworder= findViewById(R.id.customer_ARView_newOrder);
        pending= findViewById(R.id.customer_ARView_pending);
        home = findViewById(R.id.customer_ARView_my_orders);
        tailornearme = findViewById(R.id.customer_ARView_tailor_near_me);
        //      GENERATING TAILORS LIST
//        generateTailorsList(fragmentTransaction,fragmentManager);


        //     INTENT TO MEASUREMENTS SCREEN
        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, Measurements.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, Gallery.class);
                startActivity(intent);
            }
        });

        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, Settings.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, CustomerNotification.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, CustomerHistory.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, Home_Customer.class);
                startActivity(intent);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, CustomerPending.class);
                startActivity(intent);
            }
        });

        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this, CustomerNewOrders.class);
                startActivity(intent);
            }
        });

        tailornearme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ARView.this,FindTailor.class);
                startActivity(intent);
            }
        });
    }

//    //         Generates  tailors list and add it into the fragment
//    public  void  generateTailorsList(FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ){
//
//
//        ArrayList<String> sample_data =new ArrayList<>();
//        sample_data.add("abc");
//        sample_data.add("abc");
//        sample_data.add("abc");
//        sample_data.add("abc");
//        sample_data.add("abc");
//
//        for(int i= 0; i<sample_data.size(); i++){
//            Customer_List_Fragment tailors_list_fragment  = new Customer_List_Fragment(sample_data,i);
//            fragmentTransaction.add(R.id.customer_fragment_container,tailors_list_fragment);
//        }
//
//        fragmentTransaction.commit();
//
//    }
//
//    private void removeAllFragments(FragmentManager fragmentManager) {
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        }
//    }
}

