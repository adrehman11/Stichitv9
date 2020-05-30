package com.example.stichitv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Notification_list_fragment extends Fragment {

    private ArrayList<String> product;
    private String             imageUrl;
    int                        position;

    public Notification_list_fragment(ArrayList<String> product, int position) {
        this.product  = product;
        this.position = position;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout l   = (LinearLayout) inflater.inflate(R.layout.notification_card, container, false);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(),NotificationOrderDetails.class);
                startActivity(intent);
            }
        });

        return  l;
    }
}

