package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditShirtMeasurements extends AppCompatActivity {

    private Button saveeditbtn,skitbtn;
    private ImageView back_btn_editshirtmeasurements, edittrouser;
    private RequestQueue queue;
    private EditText shirt_length,Shirt_neck,Shirt_chest,Shirt_waist,Shirt_backwidthck,Shirt_Hips,Shirt_sleeevelenght,Shirt_Shoulder
            ,Shirt_QuaterSleeveLength,Shirt_wrist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shirt_measurements);

        saveeditbtn = findViewById(R.id.editshirtmeasuremnts_saveButton);
        Intent intent           = getIntent();
        final String screen      = intent.getStringExtra("screen");

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_editshirtmeasurements =  findViewById(R.id.back_btn_editshirtmeasuremnts);

        final JSONObject post_data = new JSONObject();
        queue= Volley.newRequestQueue(EditShirtMeasurements.this);


        shirt_length =  findViewById(R.id.editshirtmeasuremnts_Shirt_length);
        Shirt_neck = findViewById(R.id.editshirtmeasuremnts_Shirt_neck);
        Shirt_chest = findViewById(R.id.editshirtmeasuremnts_Shirt_chest);
        Shirt_waist = findViewById(R.id.editshirtmeasuremnts_Shirt_waist);
        Shirt_backwidthck = findViewById(R.id.editshirtmeasuremnts_Shirt_backwidth);
        Shirt_Hips = findViewById(R.id.editshirtmeasuremnts_Shirt_Hips);
        Shirt_sleeevelenght = findViewById(R.id.editshirtmeasuremnts_Shirt_sleeevelenght);
        Shirt_Shoulder = findViewById(R.id.editshirtmeasuremnts_Shirt_Shoulder);
        Shirt_QuaterSleeveLength = findViewById(R.id.editshirtmeasuremnts_Shirt_QuaterSleeveLength);
        Shirt_wrist = findViewById(R.id.editshirtmeasuremnts_Shirt_wrist);



        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_editshirtmeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),Measurements.class);
                intent.putExtra("screen",screen);
                startActivity(intent);
            }
        });

        saveeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String   shirt_lengths =  shirt_length.getText().toString();
                String   Shirt_necks = Shirt_neck.getText().toString();
                String  Shirt_chests = Shirt_chest.getText().toString();
                String Shirt_waists = Shirt_waist.getText().toString();
                String  Shirt_backwidthcks = Shirt_backwidthck.getText().toString();
                String Shirt_Hipss = Shirt_Hips.getText().toString();
                String  Shirt_sleeevelenghts = Shirt_sleeevelenght.getText().toString();
                String  Shirt_Shoulders = Shirt_Shoulder.getText().toString();
                String  Shirt_QuaterSleeveLengths = Shirt_QuaterSleeveLength.getText().toString();
                String  Shirt_wrisst = Shirt_wrist.getText().toString();

                String urli = Config.url;
                String temp  = urli+"addmeasurements/shirt";

                queue= Volley.newRequestQueue(EditShirtMeasurements.this);
                try {
                    post_data.put("id", Home_Customer.user_id);
                    post_data.put("utype",Home_Customer.utype);
                    post_data.put("shirt_length",shirt_lengths);
                    post_data.put("Shirt_neck",Shirt_necks);
                    post_data.put("Shirt_waist",Shirt_waists);
                    post_data.put("Shirt_chest",Shirt_chests);
                    post_data.put("Shirt_backwidth",Shirt_backwidthcks);
                    post_data.put("Shirt_Hips",Shirt_Hipss);
                    post_data.put("Shirt_sleeevelenght",Shirt_sleeevelenghts);
                    post_data.put("Shirt_Shoulders",Shirt_Shoulders);
                    post_data.put("Shirt_QuaterSleeveLengths",Shirt_QuaterSleeveLengths);
                    post_data.put("Shirt_wrisst",Shirt_wrisst);
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if(message.equals("measurement updated"))
                                {
                                    Intent intent = new Intent(EditShirtMeasurements.this,Measurements.class);
                                    intent.putExtra("screen",screen);
                                    startActivity(intent);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Toast.makeText(editprofile.this, "no key: 'id' in reponse", Toast.LENGTH_SHORT).show();
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
                    Log.d("asd",e.getMessage());
                }


            }
        });




    }
}


