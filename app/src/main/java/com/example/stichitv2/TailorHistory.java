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

public class TailorHistory extends AppCompatActivity {

    private ImageView tailor_profile_btn;
    private TextView myorders, gallery, history, notification, pending,name, neworders, arview,profile_name,rejectedOrder,rating;

    final JSONObject post_data = new JSONObject();
    private RequestQueue queue1;
    String urli = Config.url;
    String temp  = urli+"order2/myorder/pending";

    public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        progressDialog = new ProgressDialog((TailorHistory.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        queue1= Volley.newRequestQueue(TailorHistory.this);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        rating= findViewById(R.id.textView34);
        rating.setText(Home_Tailor.ratingvalue);
        //      GENERATING CUSTOMERS LIST
        try {
            generateCustomersList(fragmentTransaction, fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        name       = findViewById(R.id.historyscreen_TailorName);
        name.setText(Home_Tailor.name);
        tailor_profile_btn       = findViewById(R.id.historyscreen_profile_btn);
        myorders = findViewById(R.id.historyscreen_my_orders);
        gallery = findViewById(R.id.historyscreen_3d_gallery);
        history = findViewById(R.id.historyscreen_history);
        notification = findViewById(R.id.historyscreen_notification);
        neworders = findViewById(R.id.historyscreen_new_orders);
        pending = findViewById(R.id.historyscreen_pending);
        arview = findViewById(R.id.historyscreen_arView);
        rejectedOrder = findViewById(R.id.History_rejectedOrder2);



        profile_name = findViewById(R.id.welcomeback_tailor);
       // profile_name.setText(name);

        //     INTENT TO PROFILE SCREEN
        tailor_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this, Profile.class);
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this,RejectedOrderTailor.class);
                startActivity(intent);
            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this, Home_Tailor.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this, Home_Tailor.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this, NotificationTailor.class);
                startActivity(intent);
            }
        });

        neworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this, TailorNewOrder.class);
                startActivity(intent);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorHistory.this, TailorPendingOrders.class);
                startActivity(intent);
            }
        });

//        arview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(History.this,TailorARView.class);
//                startActivity(intent);
//            }
//        });

        // CALENDAR VIEW SETTINGS START

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .showBottomText(false)
                .formatTopText("EEE")
                .end()
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //do something
            }
        });

        // CALENDAR VIEW SETTINGS END

    }


    //         Generates  tailors list and add it into the fragment
    public  void  generateCustomersList(final FragmentTransaction fragmentTransaction, FragmentManager fragmentManager ) throws JSONException {


        final ArrayList<Orders> orders = new ArrayList<>();
        post_data.put("id", Home_Tailor.user_id);
        post_data.put("utype", Home_Tailor.utype);
        post_data.put("status","Finish");
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
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject resdata = array.getJSONObject(i);
                        String ID = resdata.getString("orderID");
                        String orderID = resdata.getString("ordersID");
                        String orderdate = resdata.getString("orderDate");
                        String tailorname = resdata.getString("tailorName");
                        String image = resdata.getString("image");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID, tailorname, orderdate, orderID, image, dresstype);
                        orders.add(temp_o);
                    }
                    for (int i = 0; i < orders.size(); i++) {
                        Tailor_history_fragment tailor_history_fragment = new Tailor_history_fragment(orders, i);
                        fragmentTransaction.add(R.id.tailor_history_fragment_container, tailor_history_fragment);
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
        queue1.add(getRequest);

    }

}
