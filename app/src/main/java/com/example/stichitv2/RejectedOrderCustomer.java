package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class RejectedOrderCustomer extends AppCompatActivity {

    private TextView        measurements, gallery, notification, history,tailorNearMe,profile_name,neworder,rejectedOrder;
    private ImageView       customer_profile_icon, customer_settings_icon;
    public static  String user_id,utype,name;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp  = urli+"order2/myorder/HRP";
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_order_customer);
        progressDialog = new ProgressDialog((RejectedOrderCustomer.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //      DECLARATION OF  LAYOUT REFERENCE
        measurements                = findViewById(R.id.rejectedordercustomer_measurements);
        customer_settings_icon       = findViewById(R.id.rejectedordercustomer_settings_icon_myorders);
        gallery = findViewById(R.id.rejectedordercustomer_gallery);
        notification = findViewById(R.id.rejectedordercustomer_notification);
        tailorNearMe = findViewById(R.id.rejectedordercustomer_tailor_near_me);
        neworder = findViewById(R.id.rejectedordercustomer_newOrder);
        history = findViewById(R.id.rejectedordercustomer_history);
        profile_name = findViewById(R.id.rejectedordercustomername_myOrders);
        queue= Volley.newRequestQueue(RejectedOrderCustomer.this);
        rejectedOrder = findViewById(R.id.CustomerrejectedOrder);
        profile_name.setText(Home_Customer.name);


        queue= Volley.newRequestQueue(RejectedOrderCustomer.this);

        //      GENERATING CUSTOMERS LIST
        try {
            generateCustomersList(fragmentTransaction,fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //     INTENT TO PROFILE SCREEN

        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tailorNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,FindTailor.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });
//        rejectedOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RejectedOrderCustomer.this,RejectedOrderTailor.class);
//                startActivity(intent);
//            }
//        });

        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,Measurements.class);
                intent.putExtra("screen","edit");
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,Gallery.class);
                startActivity(intent);
            }
        });
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,CustomerNewOrders.class);
                startActivity(intent);
            }
        });

        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,Settings.class);
                intent.putExtra("screen_name","Customer");
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,CustomerNotification.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RejectedOrderCustomer.this,CustomerHistory.class);
                startActivity(intent);
            }
        });





    }

    //         Generates  tailors list and add it into the fragment
    public  void  generateCustomersList(final FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ) throws JSONException {

        final ArrayList<Orders> orders = new ArrayList<>();
        post_data.put("id",Home_Customer.user_id);
        post_data.put("utype",Home_Customer.utype);
        post_data.put("status","Rejected");

        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Rehman", "onresp" + response.toString());
                    JSONArray array = response.getJSONArray("resData");
                    if(array.length() <= 0 )
                    {
                        progressDialog.dismiss();
                    }
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject resdata = array.getJSONObject(i);
                        String ID = resdata.getString("orderID");
                        String ordersID = resdata.getString("ordersID");
                        String orderdate = resdata.getString("orderDate");
                        String tailorname = resdata.getString("tailorName");
                        String image = resdata.getString("image");
                        String tailorLocation = resdata.getString("tailorLocation");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID ,tailorname,orderdate,ordersID, image,tailorLocation, dresstype);
                        orders.add(temp_o);
                    }

                    for(int i= 0; i<orders.size(); i++){
                        Rejected_Order_List_customer rejected_order_list_customer= new Rejected_Order_List_customer(orders,i);
                        fragmentTransaction.add(R.id.rejectedordercusomer_fragment_container,rejected_order_list_customer);
                    }

                    fragmentTransaction.commit();


                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(getRequest);

    }
}
