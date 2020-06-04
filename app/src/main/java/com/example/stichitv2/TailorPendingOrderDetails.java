package com.example.stichitv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TailorPendingOrderDetails extends AppCompatActivity {

    private ImageView back_btn_orderDetail;

    private TextView dresstype,ordersid,ordertype,oderdeadline,phone_no,orderDate,dresstype_2,tailorname,price
            ,shir_details,trouser_details;
    private ImageView imageView;
    private CalendarView calender;
    private Button b1,b2,reassign,reorder;
    String orderID;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    long millis;
    String temp;
    String OderDates= "";
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_pending_order_details);
        progressDialog = new ProgressDialog((TailorPendingOrderDetails.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        back_btn_orderDetail =  findViewById(R.id.back_btn_tailorPendingOrderDetail);

        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TailorPendingOrders.class);
                startActivity(intent);
            }
        });
        Intent intent           = getIntent();
        orderID      = intent.getStringExtra("OID");

        queue= Volley.newRequestQueue(TailorPendingOrderDetails.this);


        imageView =  findViewById(R.id.tailorPendingOrderDetail_img1);

        dresstype =  findViewById(R.id.tailorPendingOrderDetail_dressNameOnTop);
        ordersid = findViewById(R.id.tailorPendingOrderDetail_DressID);
        ordertype = findViewById(R.id.tailorPendingOrderDetail_deliverytype);
        oderdeadline = findViewById(R.id.tailorPendingOrderDetail_expexteddate);
        tailorname = findViewById(R.id.tailorPendingOrderDetail_TailorName);
        price = findViewById(R.id.cstomerneworder__expextedprice);
        phone_no = findViewById(R.id.textView6);

        dresstype_2 = findViewById(R.id.tailorPendingOrderDetail_DressName);
        shir_details = findViewById(R.id.tailorPendingOrderDetail_shirtdetails);
        trouser_details = findViewById(R.id.tailorPendingOrderDetail_trouserdetails);
        //orderDate = findViewById(R.id.customerPendingOrderDetail_newexpexteddate);

        try
        {

            Date curDate = new Date();
            millis = curDate.getTime();

        }
        catch (Exception e)
        {
            Log.d("Rehman","date ni chali");
        }
        calender = findViewById(R.id.tailorPendingOrderDetail_newexpexteddate);
        calender.setDate(millis);
        calender.setMinDate(millis);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                OderDates= (i1+1)+"/"+i2+"/"+i;
            }
        });
        reorder =  findViewById(R.id.tailorPendingOrderDetail_save);

        reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                try {
                    post_data.put("id",Home_Tailor.user_id);
                    post_data.put("utype",Home_Tailor.utype);
                    post_data.put("oid",orderID);
                    post_data.put("orderdate",OderDates);
                    String temp = urli+"order2/reordertailor";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if (message.equals("order reassigned")){
                                    Intent intent = new Intent(v.getContext(),Home_Tailor.class);
                                    startActivity(intent);
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

        try {
            post_data.put("id", Home_Tailor.user_id);
            post_data.put("utype", Home_Tailor.utype);
            post_data.put("oid",orderID);
            temp = urli+"order2/myorder/peending/details";
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    String message = null;

                    try {
                        message = response.getString("message");
                        if (message.equals("details")){


                            byte[] decodedString = Base64.decode(response.getString("image"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView.setImageBitmap(decodedByte);

                            dresstype .setText(response.getString("dresstype"));
                            ordersid .setText(response.getString("ordersID"));
                            ordertype .setText(response.getString("odertype"));
                            oderdeadline .setText(response.getString("OderDeadline"));
                            tailorname .setText(response.getString("tailorname"));
                            phone_no.setText(response.getString("phoneno"));
                            price.setText(response.getString("Dressprice"));

                            //calender.setDate(millis);
                            dresstype_2.setText(response.getString("dresstype"));
                            shir_details .setText(response.getString("shirtDetails"));
                            trouser_details .setText(response.getString("trouserDetails"));
                            progressDialog.dismiss();
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
}
