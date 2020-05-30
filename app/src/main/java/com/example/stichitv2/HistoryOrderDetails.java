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

public class HistoryOrderDetails extends AppCompatActivity {

    private ImageView back_btn_orderDetail;
    private TextView dresstype,ordersid,ordertype,oderdeadline,phone_no,price,dresstype_2,tailorname
            ,shir_details,trouser_details,sticthtype,lace,pipeing,buttons,comment,ordercompletedDate,orderstarted;
    private ImageView imageView;
    String orderID;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp;
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_details);
        progressDialog = new ProgressDialog((HistoryOrderDetails.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        Intent intent           = getIntent();
        orderID      = intent.getStringExtra("OID");

        queue= Volley.newRequestQueue(HistoryOrderDetails.this);

        back_btn_orderDetail =  findViewById(R.id.back_btn_historyorderDetail);

        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TailorHistory.class);
                startActivity(intent);
            }
        });
        imageView =  findViewById(R.id.historyOrderDetails_img7);



        dresstype =  findViewById(R.id.historyOrderDetails_DressNameOnTop);
        ordersid = findViewById(R.id.historyOrderDetails_DressID);
        ordertype = findViewById(R.id.historyOrderDetails_deliverytype);
        oderdeadline = findViewById(R.id.historyOrderDetails_expexteddate);
        tailorname = findViewById(R.id.historyOrderDetails_customerName);
        phone_no = findViewById(R.id.textView8);
        price = findViewById(R.id.historyOrderDetails_expextedprice);
        orderstarted = findViewById(R.id.historyOrderDetails_orderStarted);
        ordercompletedDate = findViewById(R.id.historyOrderDetails_orderCompleted);
        dresstype_2 = findViewById(R.id.historyOrderDetails_DressName);
        shir_details = findViewById(R.id.historyOrderDetails_shirtdetails);
        trouser_details = findViewById(R.id.historyOrderDetails_trouserdetails);
        sticthtype = findViewById(R.id.historyOrderDetails_stitchType);
        lace = findViewById(R.id.historyOrderDetails__lace);
        pipeing = findViewById(R.id.historyOrderDetails__piping);
        buttons = findViewById(R.id.historyOrderDetails_buttons);
        comment= findViewById(R.id.historyOrderDetails_comments);

        try {
            post_data.put("id", Home_Tailor.user_id);
            post_data.put("utype", Home_Tailor.utype);
            post_data.put("oid",orderID);
            temp = urli+"order2/myorder/peending/details";
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
                            orderstarted .setText(response.getString("orderstartedDate"));
                            dresstype_2.setText(response.getString("dresstype"));
                            shir_details .setText(response.getString("shirtDetails"));
                            trouser_details .setText(response.getString("trouserDetails"));
                            sticthtype .setText(response.getString("stichtype"));
                            lace .setText(response.getString("lace"));
                            pipeing .setText(response.getString("pipe"));
                            buttons.setText(response.getString("button"));
                            comment.setText(response.getString("coments"));
                            ordercompletedDate.setText(response.getString("OderDeadline"));
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



