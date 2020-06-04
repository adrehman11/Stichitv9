package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EditTrouserMeasurements extends AppCompatActivity {

    private Button saveeditbtn;
    private ImageView back_btn_edittrousermeasurements, editshirt;
    private RequestQueue queue;
    private EditText trouser_length,trouser_calf,trouser_ankle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trouser_measurements);

        saveeditbtn = findViewById(R.id.edittrousermeasuremnts_saveButton);

        Intent intent           = getIntent();
        final String screen      = intent.getStringExtra("screen");

        final JSONObject post_data = new JSONObject();
        trouser_length =  findViewById(R.id.edittrousermeasuremnts_length);
        trouser_calf = findViewById(R.id.edittrousermeasuremnts_calf);
        trouser_ankle = findViewById(R.id.edittrousermeasuremnts_ankle);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_edittrousermeasurements =  findViewById(R.id.back_btn_edittrousermeasuremnts);

        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_edittrousermeasurements.setOnClickListener(new View.OnClickListener() {
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

                String  trouser_lengths = trouser_length.getText().toString();
                String  trouser_calfs = trouser_calf.getText().toString();
                String  trouser_ankles= trouser_ankle.getText().toString();

                String urli = Config.url;
                String temp  = urli+"addmeasurements/trouser";

                queue= Volley.newRequestQueue(EditTrouserMeasurements.this);
                try {
                    post_data.put("id", Home_Customer.user_id);
                    post_data.put("utype",Home_Customer.utype);
                    post_data.put("trouser_length",trouser_lengths);
                    post_data.put("trouser_calf",trouser_calfs);
                    post_data.put("trouser_ankle",trouser_ankles);

                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if(message.equals("measurement updated"))
                                {
                                    Intent intent = new Intent(EditTrouserMeasurements.this,Measurements.class);
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
            }
        );

    }
}


