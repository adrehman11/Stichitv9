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

public class CustomerPending extends AppCompatActivity {

    private TextView measurements, gallery, notification, history, neworder, home ,arView, tailornearme,profile_name,rejectedOrder;
    private ImageView customer_profile_icon, customer_settings_icon;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp  = urli+"order2/myorder/pending";
    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pending);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        progressDialog = new ProgressDialog((CustomerPending.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );


        //      DECLARATION OF  LAYOUT REFERENCE
        measurements                = findViewById(R.id.customerPending_measurements);
        customer_settings_icon       = findViewById(R.id.customer_settings_icon_pending);
        gallery = findViewById(R.id.customerPending_gallery);
        notification = findViewById(R.id.customerPending_notification);
        history = findViewById(R.id.customerPending_history);
        neworder= findViewById(R.id.customerPending_newOrder);
        home= findViewById(R.id.customerPending_my_orders);
        arView = findViewById(R.id.customerPending_arView);
        tailornearme = findViewById(R.id.customerPending_tailor_near_me);
        rejectedOrder = findViewById(R.id.customerrejectedOrder4);

        profile_name = findViewById(R.id.name_customerPending);
        queue= Volley.newRequestQueue(CustomerPending.this);

        profile_name.setText(Home_Customer.name);

        //      GENERATING TAILORS LIST
        try {
            generateTailorsList(fragmentTransaction,fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //     INTENT TO MEASUREMENTS SCREEN
        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, Measurements.class);
                intent.putExtra("screen","edit");
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, Gallery.class);
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this,RejectedOrderCustomer.class);
                startActivity(intent);
            }
        });

        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, Settings.class);
                intent.putExtra("screen_name","Customer");
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, CustomerNotification.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, CustomerHistory.class);
                startActivity(intent);
            }
        });

        arView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, CustomerHistory.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, Home_Customer.class);
                startActivity(intent);
            }
        });

        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this, CustomerNewOrders.class);
                startActivity(intent);
            }
        });

        tailornearme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerPending.this,FindTailor.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });
    }

    //         Generates  tailors list and add it into the fragment
    public  void  generateTailorsList(final FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ) throws JSONException {


        final ArrayList<Orders> orders = new ArrayList<>();
        post_data.put("id", Home_Customer.user_id);
        post_data.put("utype", Home_Customer.utype);
        post_data.put("status","Pending");
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
                        String orderdate = resdata.getString("orderDate");
                        String tailorname = resdata.getString("tailorName");
                        String image = resdata.getString("image");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID , tailorname, orderdate,image,dresstype);
                        orders.add(temp_o);
                    }

                    for(int i= 0; i<orders.size(); i++){
                        Customer_Pending_List customer_pending_list  = new Customer_Pending_List(orders,i);
                        fragmentTransaction.add(R.id.customer_fragment_container_pending,customer_pending_list);
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

