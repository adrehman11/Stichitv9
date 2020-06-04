package com.example.stichitv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Customer_History_List extends Fragment {

    private ArrayList<Orders>   orders;
    private String             imageUrl;
    int                        position;
    private TextView ordersid,orderdate,tailorname,tailorlocation,dresstype;
    ImageView imageView;

    public Customer_History_List(ArrayList<Orders> orders, int position) {
        this.orders  = orders;
        this.position = position;
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout l   = (LinearLayout) inflater.inflate(R.layout.customer_history_list, container, false);
        ordersid =  l.findViewById(R.id.customer_id);
        ordersid     .setText(orders.get(position).getOrderID());

        orderdate =  l.findViewById(R.id.tailorneworderdate);
        orderdate     .setText(orders.get(position).getOrderdate());

        tailorname =  l.findViewById(R.id.address);
        tailorname     .setText(orders.get(position).getTailorname());

        dresstype =  l.findViewById(R.id.dress_name);
        dresstype     .setText(orders.get(position).getDresstype());

        imageView =  l.findViewById(R.id.dress_img);
        byte[] decodedString = Base64.decode(orders.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);

        CustomerHistory.progressDialog.dismiss();

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(),CustomerHistoryOrderDetails.class);
                intent.putExtra("OID",orders.get(position).getID());
                startActivity(intent);
            }
        });


        return  l;
    }

}
