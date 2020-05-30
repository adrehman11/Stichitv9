package com.example.stichitv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class CustomerNewOrders extends AppCompatActivity {

    private TextView measurements, gallery, notification, history, home, pending,arView, tailorNearMe,myorder,profile_name,rejectedOrder;
    private ImageView customer_profile_icon, customer_settings_icon;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp  = urli+"order/myorder/requests";
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_new_orders);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        progressDialog = new ProgressDialog((CustomerNewOrders.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );


        queue= Volley.newRequestQueue(CustomerNewOrders.this);

        //      DECLARATION OF  LAYOUT REFERENCE
        measurements                = findViewById(R.id.newOrder_measurements);
        customer_settings_icon       = findViewById(R.id.customer_settings_icon_newOrder);
        gallery = findViewById(R.id.newOrder_gallery);
        notification = findViewById(R.id.newOrder_notification);
        history = findViewById(R.id.newOrder_history);
        pending= findViewById(R.id.newOrder_pending);
        arView = findViewById(R.id.newOrder_arView);
        tailorNearMe = findViewById(R.id.newOrder_tailor_near_me);
        myorder = findViewById(R.id.newOrder_my_orders);
        profile_name = findViewById(R.id.welcome_back);
        rejectedOrder = findViewById(R.id.customerrejectedOrder2);

        profile_name.setText(Home_Customer.name);
        //      GENERATING TAILORS LIST
        try {
            generateTailorsList(fragmentTransaction,fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNewOrders.this,Home_Customer.class);
                startActivity(intent);
            }
        });
        tailorNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNewOrders.this,FindTailor.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNewOrders.this,RejectedOrderCustomer.class);
                startActivity(intent);
            }
        });

        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNewOrders.this,Measurements.class);
                intent.putExtra("screen","edit");
                startActivity(intent);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNewOrders.this,CustomerPending.class);
                startActivity(intent);
            }
        });

//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CustomerNewOrders.this,Gallery.class);
//                startActivity(intent);
//            }
//        });


        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerNewOrders.this,Settings.class);
                intent.putExtra("screen_name","Customer");
                startActivity(intent);
            }
        });

//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CustomerNewOrders.this,CustomerNotification.class);
//                startActivity(intent);
//            }
//        });

//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CustomerNewOrders.this,CustomerHistory.class);
//                startActivity(intent);
//            }
//        });

        //     INTENT TO MEASUREMENTS SCREEN



        //     INTENT TO PROFILE SCREEN

    }

    //         Generates  tailors list and add it into the fragment
    public  void  generateTailorsList(final FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ) throws JSONException {

        final ArrayList<Orders> orders = new ArrayList<>();
                    post_data.put("id",Home_Customer.user_id);
                    post_data.put("utype",Home_Customer.utype);
                    post_data.put("status","pending");

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
                        String orderID = resdata.getString("ordersID");
                        String orderdate = resdata.getString("orderDate");
                        String tailorname = resdata.getString("tailorName");
                        String tailorlocation = resdata.getString("tailorLocation");
                        String image = resdata.getString("image");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID , tailorname, orderdate,orderID,image,tailorlocation,dresstype);
                        orders.add(temp_o);
                    }

                    for(int i= 0; i<orders.size(); i++){
                        Customer_NewOrder_List customer_newOrder_list = new Customer_NewOrder_List(orders,i);
                        fragmentTransaction.add(R.id.customer_fragment_container_newOrder,customer_newOrder_list);
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

    private void removeAllFragments(FragmentManager fragmentManager) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


}


