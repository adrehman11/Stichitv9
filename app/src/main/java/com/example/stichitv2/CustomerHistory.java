package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class CustomerHistory extends AppCompatActivity {


    private TextView measurements, gallery, notification, myorders, neworder, pending, arView, tailornearme,rejectedOrder,profile_name;
    private ImageView customer_profile_icon, customer_settings_icon;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp = urli + "order2/myorder/pending";
    public static ProgressDialog progressDialog;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_history);
        myDialog = new Dialog(this);
        queue= Volley.newRequestQueue(CustomerHistory.this);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        progressDialog = new ProgressDialog((CustomerHistory.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        //      DECLARATION OF  LAYOUT REFERENCE
        measurements = findViewById(R.id.historycustomer_measurements);
        customer_settings_icon = findViewById(R.id.historycustomer_settings_icon);
        gallery = findViewById(R.id.historycustomer_gallery);
        notification = findViewById(R.id.historycustomer_notification);
        myorders = findViewById(R.id.historycustomer_my_orders);
        neworder = findViewById(R.id.historycustomer_newOrder);
        pending = findViewById(R.id.historycustomer_pending);
        arView = findViewById(R.id.historycustomer_arView);
        tailornearme = findViewById(R.id.historycustomer_tailor_near_me);
        rejectedOrder = findViewById(R.id.customerrejectedOrder3);
        profile_name = findViewById(R.id.customerName_history);


        profile_name.setText(Home_Customer.name);

        //      GENERATING TAILORS LIST
        try {
            generateTailorsList(fragmentTransaction, fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //     INTENT TO MEASUREMENTS SCREEN
        measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, Measurements.class);
                intent.putExtra("screen", "edit");
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, Gallery.class);
                startActivity(intent);
            }
        });

        //     INTENT TO PROFILE SCREEN
        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, Settings.class);
                intent.putExtra("screen_name", "Customer");
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, CustomerNotification.class);
                startActivity(intent);
            }
        });

        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, Home_Customer.class);
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this,RejectedOrderCustomer.class);
                startActivity(intent);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, CustomerPending.class);
                startActivity(intent);
            }
        });

        arView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, ARView.class);
                startActivity(intent);
            }
        });

        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this, CustomerNewOrders.class);
                startActivity(intent);
            }
        });   pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this,CustomerPending.class);
                startActivity(intent);
            }
        });



        tailornearme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHistory.this,FindTailor.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });

    }

    //         Generates  tailors list and add it into the fragment
    public void generateTailorsList(final FragmentTransaction fragmentTransaction, FragmentManager fragmentManager) throws JSONException {


        final ArrayList<Orders> orders = new ArrayList<>();
        post_data.put("id", Home_Customer.user_id);
        post_data.put("utype", Home_Customer.utype);
        post_data.put("status","Finish");
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Rehman", "onresp" + response.toString());
                    JSONArray array = response.getJSONArray("resData");
                    JSONArray array2 = response.getJSONArray("resdata2");
                    if(array.length() <= 0 )
                    {
                        progressDialog.dismiss();
                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject resdata = array.getJSONObject(i);
                        String ID = resdata.getString("orderID");
                        String orderID = resdata.getString("ordersID");
                        String orderdate = resdata.getString("orderDate");
                        String tailorname = resdata.getString("tailorName");
                        // String tailorlocation = resdata.getString("tailorLocation");
                        String image = resdata.getString("image");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID, tailorname, orderdate, orderID, image, dresstype);
                        orders.add(temp_o);
                    }

                    for (int i = 0; i < orders.size(); i++) {
                        Customer_History_List customer_history_list = new Customer_History_List(orders, i);
                        fragmentTransaction.add(R.id.customer_history_fragment_container, customer_history_list);
                    }

                    fragmentTransaction.commit();
                    if(array2.length() > 0 ) {
                        for (int i = 0; i < 1; i++) {
                            JSONObject resdata2 = array2.getJSONObject(i);
                            ShowPopup(resdata2.getString("ordersID2"),resdata2.getString("dresstype2")
                                    ,resdata2.getString("tailorName2"));
                        }
                    }


                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        queue.add(getRequest);
    }
    private void removeAllFragments (FragmentManager fragmentManager){
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
    public void ShowPopup(final String id,String dresstype,String tailorname) {
        final TextView txtclose,txt2;
        Button notnow;
        RatingBar ratingbar;

        myDialog.setContentView(R.layout.ratingorder);
        txtclose = (TextView) myDialog.findViewById(R.id.discription);
        txt2 = (TextView) myDialog.findViewById(R.id.discription2);
        notnow = (Button) myDialog.findViewById(R.id.notnow);
        ratingbar= (RatingBar) myDialog.findViewById(R.id.ratingbar);
        txtclose.setText("Please Rate Your Order");
        txt2.setText("Your Order("+id+") is Ready. Take your Dress("+dresstype+") From "+tailorname+" Shop");
        notnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                try {
                    post_data.put("ordersid",id);
                    post_data.put("ratingvalue",String.valueOf(rating));
                    post_data.put("id",Home_Customer.user_id);
                    post_data.put("utype",Home_Customer.utype);
                    String temp1= Config.url+"order2/submitrating";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp1, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            try {
                                if(response.getString("message").equals("DoneRating")){
                                    myDialog.dismiss();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Rehman", String.valueOf(error));
                        }
                    });
                    queue.add(getRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }
}



