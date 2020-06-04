package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Home_Tailor extends AppCompatActivity {


    private ImageView tailor_profile_btn;
    private TextView orders, gallery, history, notification,profile_name,neworder,rejectedOrder,pending,rating;
    public static  String user_id,utype,name,ratingvalue;

    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp  = urli+"order/currentOrder";
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__tailor);

        progressDialog = new ProgressDialog((Home_Tailor.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        queue= Volley.newRequestQueue(Home_Tailor.this);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        user_id = preferences.getString("id","");
        utype = preferences.getString("utype","");
        name = preferences.getString("name","");
        ratingvalue = preferences.getString("rating","");
        rating= findViewById(R.id.textView26);
        rating.setText(ratingvalue);
        profile_name = findViewById(R.id.welcomeback_tailor);

        profile_name.setText(name);

        //      DECLARATION OF  LAYOUT REFERENCE
        tailor_profile_btn       = findViewById(R.id.tailor_profile_btn);
        orders = findViewById(R.id.tailor_my_orders);
       // gallery = findViewById(R.id.tailor_3d_gallery);
        history = findViewById(R.id.tailor_history);
        neworder = findViewById(R.id.tailor_new_orders);
        notification = findViewById(R.id.tailor_notification);
        rejectedOrder = findViewById(R.id.RejectedOrder);
        pending= findViewById(R.id.tailor_pending);



        //      GENERATING CUSTOMERS LIST
        try {
            generateCustomersList(fragmentTransaction,fragmentManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //     INTENT TO PROFILE SCREEN
        tailor_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Tailor.this,Profile.class);
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Tailor.this,RejectedOrderTailor.class);
                startActivity(intent);
            }
        });
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Tailor.this,TailorNewOrder.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home_Tailor.this,Home_Tailor.class);
//                startActivity(intent);
//            }
//        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Tailor.this, TailorHistory.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Tailor.this,NotificationTailor.class);
                startActivity(intent);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Tailor.this,TailorPendingOrders.class);
                startActivity(intent);
            }
        });
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
        post_data.put("id",Home_Tailor.user_id);
        post_data.put("utype",Home_Tailor.utype);
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
                        String image = resdata.getString("image");
                        String dresstype = resdata.getString("dresstype");
                        Orders temp_o = new Orders(ID , tailorname, orderdate,orderID,image,dresstype);
                        orders.add(temp_o);
                    }
                    for(int i= 0; i<orders.size(); i++){
                        Tailor_List_Fragment tailor_list_fragment= new Tailor_List_Fragment(orders,i);
                        fragmentTransaction.add(R.id.tailor_fragment_container,tailor_list_fragment);
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
