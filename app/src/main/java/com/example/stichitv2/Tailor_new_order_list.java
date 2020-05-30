package com.example.stichitv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tailor_new_order_list  extends Fragment {

    private ArrayList<Orders>   orders;
    int                        position;
    private TextView ordersid,orderdate,tailorname,dresstype;
    ImageView imageView;
    private Button accept,reject;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;

    public Tailor_new_order_list(ArrayList<Orders> orders, int position) {
        this.orders  = orders;
        this.position = position;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout l   = (LinearLayout) inflater.inflate(R.layout.tailor_newordercard, container, false);

        queue= Volley.newRequestQueue(inflater.getContext());



        orderdate =  l.findViewById(R.id.tailorneworderdate);
        orderdate     .setText(orders.get(position).getOrderdate());

        tailorname =  l.findViewById(R.id.customerNotification_name);
        tailorname     .setText(orders.get(position).getTailorname());

        dresstype =  l.findViewById(R.id.dress_type);
        dresstype     .setText(orders.get(position).getDresstype());

        imageView =  l.findViewById(R.id.customerNotification_img);
        byte[] decodedString = Base64.decode(orders.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        TailorNewOrder.progressDialog.dismiss();
        accept =  l.findViewById(R.id.accept_customerNotification);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    post_data.put("id",Home_Tailor.user_id);
                    post_data.put("utype",Home_Tailor.utype);
                    post_data.put("oid",orders.get(position).getID());
                    String temp =urli+"order/accept";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("orderaccepted")){
                                    Intent i = new Intent(inflater.getContext(),TailorNewOrder.class);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(getRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        reject =  l.findViewById(R.id.reject_customerNotification);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    post_data.put("id",Home_Tailor.user_id);
                    post_data.put("utype",Home_Tailor.utype);
                    post_data.put("oid",orders.get(position).getID());
                   String temp = urli+"order/orderreject";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("orderrejected")){
                                    Intent i = new Intent(inflater.getContext(),TailorNewOrder.class);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    queue.add(getRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(), TailorNewOrderDetails.class);
                intent.putExtra("OID",orders.get(position).getID());
                startActivity(intent);
            }
        });




        return  l;
    }
}
