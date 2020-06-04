package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.File;
import java.util.ArrayList;

public class Home_Customer extends AppCompatActivity {

    private TextView        measurements, gallery, notification, history,tailorNearMe,profile_name,neworder,pending,rejectedOrder;
    private ImageView       customer_profile_icon, customer_settings_icon;
    public static  String user_id,utype,name;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    public static ProgressDialog progressDialog;
    String urli = Config.url;
    String temp  = urli+"order/currentOrder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__customer);
        //try
        progressDialog = new ProgressDialog((Home_Customer.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );



        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        user_id = preferences.getString("id","");
        utype = preferences.getString("utype","");
        name = preferences.getString("name","");

        File deletePrefFile = new File("/data/data/com.hirecraft.hirecraftmanager/shared_prefs/sendorder.xml");
          deletePrefFile.delete();

        //      DECLARATION OF  LAYOUT REFERENCE
        measurements                = findViewById(R.id.customer_measurements);
        customer_settings_icon       = findViewById(R.id.customer_settings_icon_myorders);
        gallery = findViewById(R.id.customer_gallery);
        notification = findViewById(R.id.customer_notification);
        tailorNearMe = findViewById(R.id.customer_tailor_near_me);
        neworder = findViewById(R.id.customer_newOrder);
        history = findViewById(R.id.customer_history);
        pending = findViewById(R.id.customer_pending);
        profile_name = findViewById(R.id.customerName_myOrders);
        queue= Volley.newRequestQueue(Home_Customer.this);
        rejectedOrder = findViewById(R.id.CustomerrejectedOrder);
        profile_name.setText(name);

        //      GENERATING TAILORS LIST
        try {
            generateTailorsList(fragmentTransaction,fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //     INTENT TO MEASUREMENTS SCREEN

        tailorNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,FindTailor.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,RejectedOrderCustomer.class);
                startActivity(intent);
            }
        });

        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,Measurements.class);
                intent.putExtra("screen","edit");
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,Gallery.class);
                startActivity(intent);
            }
        });
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,CustomerNewOrders.class);
                startActivity(intent);
            }
        });

        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,Settings.class);
                intent.putExtra("screen_name","Customer");
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,CustomerNotification.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,CustomerHistory.class);
                startActivity(intent);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Customer.this,CustomerPending.class);
                startActivity(intent);
            }
        });


    }

    //         Generates  tailors list and add it into the fragment
    public  void  generateTailorsList(final FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ) throws JSONException {

        final ArrayList<Orders> orders = new ArrayList<>();
        post_data.put("id",Home_Customer.user_id);
        post_data.put("utype",Home_Customer.utype);
        //post_data.put("status","");
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
                       // String tailorlocation = resdata.getString("tailorLocation");
                        String image = resdata.getString("image");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID , tailorname, orderdate,orderID,image,dresstype);
                        orders.add(temp_o);
                    }

                    for(int i= 0; i<orders.size(); i++){
                        Customer_List_Fragment customer_list_fragment  = new Customer_List_Fragment(orders,i);
                        fragmentTransaction.add(R.id.customer_fragment_container,customer_list_fragment);
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


