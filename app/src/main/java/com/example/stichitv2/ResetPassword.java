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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPassword extends AppCompatActivity {

    private EditText resetpassword,resetcpassword;


    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;

    String urli = Config.url;
    String temp  = urli+"updatepassword";
    private Button saveNewPasswird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        queue= Volley.newRequestQueue(ResetPassword.this);

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        final String id = preferences.getString("id","");
        final String utype = preferences.getString("utype","");

        resetpassword = findViewById(R.id.reset_enternewpassword);
        resetcpassword = findViewById(R.id.reset_confirmnewpassword);

        saveNewPasswird = findViewById(R.id.reset_saveButton);

        //     NAVIGATE BACK TO HOME SCREEN

        saveNewPasswird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ResetPassword = resetpassword.getText().toString();
                String ResetCPassword = resetcpassword.getText().toString();
                if (!ResetPassword.equals(ResetCPassword))
                {
                    resetcpassword.setText("");
                    Toast.makeText(ResetPassword.this,"password does not match",Toast.LENGTH_LONG).show();

                }
                else {
                    try {
                        post_data.put("password", ResetPassword);
                        post_data.put("id", id);
                        post_data.put("utype", utype);
                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("Response", "onresp" + response.toString());
                                String message = null;
                                try {
                                    message = response.getString("message");
                                    if (message.equals("password updated")) {
                                        Toast.makeText(ResetPassword.this, message, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(com.example.stichitv2.ResetPassword.this,SignIn.class);
                                        startActivity(intent);
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
        });
    }
}
