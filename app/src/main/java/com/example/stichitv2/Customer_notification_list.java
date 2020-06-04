package com.example.stichitv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Customer_notification_list extends Fragment {

    private ArrayList<String> product;
    private String             imageUrl;
    int                        position;


    public Customer_notification_list(ArrayList<String> product, int position) {
        this.product  = product;
        this.position = position;
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout l   = (LinearLayout) inflater.inflate(R.layout.customer_notification_list, container, false);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(),CutomerNotificationOrderDetails.class);
                startActivity(intent);
            }
        });


        return  l;
    }




}
