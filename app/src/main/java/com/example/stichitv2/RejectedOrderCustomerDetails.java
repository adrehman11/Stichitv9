package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RejectedOrderCustomerDetails extends AppCompatActivity {
    private ImageView back_btn_orderDetail;
    private TextView dresstype,ordersid,ordertype,oderdeadline,phone_no,orderDate,dresstype_2,tailorname
            ,shir_details,trouser_details,sticthtype,lace,pipeing,buttons,comment,price;
    private ImageView imageView;
    String orderID;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_order_customer_details);

        progressDialog = new ProgressDialog((RejectedOrderCustomerDetails.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        Intent intent           = getIntent();
        orderID      = intent.getStringExtra("OID");

        queue= Volley.newRequestQueue(RejectedOrderCustomerDetails.this);

        back_btn_orderDetail =  findViewById(R.id.back_btn_RejectedOrderDetail);

        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RejectedOrderCustomer.class);
                startActivity(intent);
            }
        });
        imageView =  findViewById(R.id.RejectedOrderDetail_img1);


        dresstype =  findViewById(R.id.RejectedOrderDetail_DressNameOnTop);
        ordersid = findViewById(R.id.RejectedOrderDetail_DresID);
        ordertype = findViewById(R.id.RejectedOrderDetail_deliverytype);
        oderdeadline = findViewById(R.id.RejectedOrderDetail_orderCompleted);
        tailorname = findViewById(R.id.RejectedOrderDetail_TailorName);
        phone_no = findViewById(R.id.textView23);
        price = findViewById(R.id.RejectedOrderDetail_priceView);
        //ordercompletedDate = findViewById(R.id.historyOrderDetails_orderCompleted);
        dresstype_2 = findViewById(R.id.RejectedOrderDetail_DressName);
        shir_details = findViewById(R.id.RejectedOrderDetail_shirtdetails);
        trouser_details = findViewById(R.id.RejectedOrderDetail_trouserdetails);
        sticthtype = findViewById(R.id.RejectedOrderDetail__stitchType);
        lace = findViewById(R.id.RejectedOrderDetail__lace);
        pipeing = findViewById(R.id.RejectedOrderDetail__piping);
        buttons = findViewById(R.id.RejectedOrderDetail__buttons);
        comment= findViewById(R.id.RejectedOrderDetail_comments);

        try {
            post_data.put("id", Home_Customer.user_id);
            post_data.put("utype", Home_Customer.utype);
            post_data.put("oid",orderID);
            temp = urli+"order2/myorder/Rejected/details";
            final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
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
                            // Log.d("Rehman",response.getString("ordersID"));
                            ordersid .setText(response.getString("ordersID"));
                            ordertype .setText(response.getString("odertype"));
                            oderdeadline .setText(response.getString("OderDeadline"));
                            tailorname .setText(response.getString("tailorname"));
                            phone_no.setText(response.getString("phoneno"));
                           // orderDate .setText(response.getString("orderDate"));
                            dresstype_2.setText(response.getString("dresstype"));
                            shir_details .setText(response.getString("shirtDetails"));
                            trouser_details .setText(response.getString("trouserDetails"));
                            sticthtype .setText(response.getString("stichtype"));
                            lace .setText(response.getString("lace"));
                            pipeing .setText(response.getString("pipe"));
                            buttons.setText(response.getString("button"));
                            comment.setText(response.getString("coments"));
                            price.setText(response.getString("Dressprice"));
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
