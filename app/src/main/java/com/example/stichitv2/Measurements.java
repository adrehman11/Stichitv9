package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Measurements extends AppCompatActivity {

    private ImageView back_btn_editshirtmeasurements, editshirtbuton, edittrouserbuton;
    private ImageView       customer_profile_icon, customer_settings_icon;
    private TextView myorder,neworder, gallery, history,profile_name, notification,tailornearme;
    private RequestQueue queue;
    private Button done;
    private TextView shirt_length,Shirt_neck,Shirt_chest,Shirt_waist,Shirt_backwidthck,Shirt_Hips,Shirt_sleeevelenght,Shirt_Shoulder
            ,Shirt_QuaterSleeveLength,Shirt_wrist,trouser_length,trouser_calf,trouser_ankle,rejectedOrder,pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);

        final SharedPreferences preferences = getSharedPreferences("sendorder",MODE_PRIVATE);
        Intent intent           = getIntent();
        final String screen      = intent.getStringExtra("screen");

        //      DECLARATION OF  LAYOUT REFERENCE


        final JSONObject post_data = new JSONObject();

        done = findViewById(R.id.Done);
        if(screen.equals("edit")){
            done.setVisibility(View.GONE);
        }
        if(screen.equals("sendorder")){
            done.setVisibility(View.VISIBLE);
        }
        profile_name = findViewById(R.id.customerName_measurements);

        profile_name.setText(Home_Customer.name);
        rejectedOrder = findViewById(R.id.customerrejectedOrder);
        pending = findViewById(R.id.measurements_pending);
         shirt_length =  findViewById(R.id.Shirt_length);
        Shirt_neck = findViewById(R.id.Shirt_neck);
        Shirt_chest = findViewById(R.id.Shirt_chest);
        Shirt_waist = findViewById(R.id.Shirt_waist);
        Shirt_backwidthck = findViewById(R.id.Shirt_backwidth);
        Shirt_Hips = findViewById(R.id.Shirt_Hips);
        Shirt_sleeevelenght = findViewById(R.id.Shirt_sleeevelenght);
        Shirt_Shoulder = findViewById(R.id.Shirt_Shoulder);
        Shirt_QuaterSleeveLength = findViewById(R.id.Shirt_QuaterSleeveLength);
        Shirt_wrist = findViewById(R.id.Shirt_wrist);
        trouser_length = findViewById(R.id.trouser_length);
        trouser_calf = findViewById(R.id.trouser_calf);
        trouser_ankle = findViewById(R.id.trouser_ankle);



        editshirtbuton = findViewById(R.id.editShirtButton);
        edittrouserbuton = findViewById(R.id.editTrouserButton);
        customer_settings_icon       = findViewById(R.id.customer_settings_iconInMeasurements);
        myorder = findViewById(R.id.measurements_my_orders);
        gallery = findViewById(R.id.measurements_gallery);
        history = findViewById(R.id.measurements_history);
        notification = findViewById(R.id.measurements_notification);
        tailornearme = findViewById(R.id.measurements_tailor_near_me);
        neworder = findViewById(R.id.measurements_newOrder);

        //     NAVIGATE BACK TO HOME SCREEN

        tailornearme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),FindTailor.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });
        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),CustomerNewOrders.class);
                intent.putExtra("screen","home");
                startActivity(intent);
            }
        });
        editshirtbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),EditShirtMeasurements.class);
                intent.putExtra("screen",screen);
                startActivity(intent);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),SendOrder.class);
                intent.putExtra("screen","2");
                startActivity(intent);
            }
        });
        rejectedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Measurements.this,RejectedOrderCustomer.class);
                startActivity(intent);
            }
        });
        edittrouserbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),EditTrouserMeasurements.class);
                intent.putExtra("screen",screen);
                startActivity(intent);
            }
        });

        customer_settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),Settings.class);
                intent.putExtra("screen_name","Customer");
                startActivity(intent);
            }
        });

        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),Home_Customer.class);
                startActivity(intent);
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Measurements.this,CustomerPending.class);
                startActivity(intent);
            }
        });


//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(),Gallery.class);
//                startActivity(intent);
//            }
//        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),CustomerHistory.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),CustomerNotification.class);
                startActivity(intent);
            }
        });
        try {
            String urli = Config.url;
            String temp  = urli+"addmeasurements/getmeasurements";
            queue= Volley.newRequestQueue(Measurements.this);
            post_data.put("id",Home_Customer.user_id);
            post_data.put("utype",Home_Customer.utype);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("Rehman", "onresp" + response.toString());
                    String message = null;
                    try {
                        message = response.getString("message");
                        if(message.equals("ok1"))
                        {
                            shirt_length.setText(response.getString("Shirt_length"));
                            Shirt_neck.setText(response.getString("Shirt_neck"));
                            Shirt_chest.setText(response.getString("Shirt_chest"));
                            Shirt_waist.setText(response.getString("Shirt_waist"));
                            Shirt_backwidthck .setText(response.getString("Shirt_backwidth"));
                            Shirt_Hips.setText(response.getString("Shirt_Hips"));
                            Shirt_sleeevelenght.setText(response.getString("Shirt_sleeevelenght"));
                            Shirt_Shoulder.setText(response.getString("Shirt_Shoulder"));
                            Shirt_QuaterSleeveLength.setText(response.getString("Shirt_QuaterSleeveLength"));
                            Shirt_wrist.setText(response.getString("Shirt_wrist"));
                            trouser_length.setText(response.getString("trouser_length"));
                            trouser_calf .setText(response.getString("trouser_calf"));
                            trouser_ankle.setText(response.getString("trouser_ankle"));

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
