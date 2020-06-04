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

public class Tailor_List_Fragment extends Fragment {


    private ArrayList<Orders>   orders;
    private String             imageUrl;
    int                        position;
    private TextView ordersid,orderdate,tailorname,tailorlocation,dresstype;
    ImageView imageView;

    public Tailor_List_Fragment(ArrayList<Orders> orders, int position) {
        this.orders  = orders;
        this.position = position;
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout l   = (LinearLayout) inflater.inflate(R.layout.customer_card, container, false);

        ordersid =  l.findViewById(R.id.customer_id);
        ordersid     .setText(orders.get(position).getOrderID());

        orderdate =  l.findViewById(R.id.cms);
        orderdate     .setText(orders.get(position).getTailorlocation());

        tailorname =  l.findViewById(R.id.customer_name);
        tailorname     .setText(orders.get(position).getTailorname());

        dresstype =  l.findViewById(R.id.dress_type);
        dresstype     .setText(orders.get(position).getDresstype());

        imageView =  l.findViewById(R.id.customer_img);
        byte[] decodedString = Base64.decode(orders.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        Home_Tailor.progressDialog.dismiss();

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(),OrderDetail.class);
                intent.putExtra("OID",orders.get(position).getID());
                startActivity(intent);
            }
        });


        return  l;
    }



}
