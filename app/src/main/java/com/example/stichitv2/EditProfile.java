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

public class EditProfile extends AppCompatActivity {

    private Button saveeditbtn;
    private ImageView back_btn_editprofile;
    private RequestQueue queue;

    private EditText fname,lname,contact;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        saveeditbtn = findViewById(R.id.saveeditprofilebtn);
        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        final JSONObject post_data = new JSONObject();
        String urli = Config.url;
        String   temp  = urli+"viewprofile/getprofile";
        Intent intent           = getIntent();
        final String screen_name      = intent.getStringExtra("screen_name");

        fname = findViewById(R.id.signup_name);
        lname = findViewById(R.id.last_name);
        contact = findViewById(R.id.signup_phoneNumber);
        password = findViewById(R.id.signup_password);

        try {


            queue= Volley.newRequestQueue(EditProfile.this);
            if(screen_name.equals("Tailor"))
            {
                post_data.put("id",Home_Tailor.user_id);
                post_data.put("utype",Home_Tailor.utype);
            }
            else if (screen_name.equals("Customer"))
            {
                post_data.put("id",Home_Customer.user_id);
                post_data.put("utype",Home_Customer.utype);
            }

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("Rehman", "onresp" + response.toString());
                    String message = null;
                    try {
                        message = response.getString("message");
                        if(message.equals("ok1"))
                        {
                            fname.setText(response.getString("firstname"));
                            lname.setText(response.getString("lastname"));
                            contact.setText(response.getString("contact"));
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

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_editprofile =  findViewById(R.id.back_btn_editprofile);

        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (screen_name.equals("Tailor"))
                {
                    Intent intent = new Intent(view.getContext(),Settings.class);
                    intent.putExtra("screen_name","Tailor");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(view.getContext(),Settings.class);
                    intent.putExtra("screen_name","Customer");
                    startActivity(intent);
                }

            }
        });

        saveeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urli = Config.url;
               String temp  = urli+"viewprofile/editprofile";
                queue= Volley.newRequestQueue(EditProfile.this);
                final String Fname = fname.getText().toString();
               final String Lname = lname.getText().toString();
                String Phone = contact.getText().toString();
                String Password = password.getText().toString();
                if(Fname.equals("")||Lname.equals("")||Phone.equals("")||Password.equals(""))
                {
                    Toast.makeText(EditProfile.this,"Please enter data in all fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    try {
                        if(screen_name.equals("Tailor"))
                        {
                            post_data.put("id",Home_Tailor.user_id);
                            post_data.put("utype",Home_Tailor.utype);
                        }
                        else if (screen_name.equals("Customer"))
                        {
                            post_data.put("id",Home_Customer.user_id);
                            post_data.put("utype",Home_Customer.utype);
                        }
                        post_data.put("firstname",Fname);
                        post_data.put("lastname",Lname);
                        post_data.put("password",Password);
                        post_data.put("contact",Phone);
                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("Rehman", "onresp" + response.toString());
                                String message = null;
                                try {
                                    message = response.getString("message");
                                    if(message.equals("profile updated"))
                                    {
                                        if(screen_name.equals("Tailor")) {
                                            Intent intent = new Intent(EditProfile.this, Profile.class);
                                            SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                            SharedPreferences.Editor editor =preferences.edit();
                                            editor.putString("name",Fname+" "+Lname);
                                            editor.apply();
                                            startActivity(intent);
                                        }
                                       else if(screen_name.equals("Customer")) {
                                            Intent intent = new Intent(EditProfile.this, Home_Customer.class);
                                            SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                            SharedPreferences.Editor editor =preferences.edit();
                                            editor.putString("name",Fname+" "+Lname);
                                            editor.apply();
                                            startActivity(intent);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("Rehman",e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                        Log.d("Rehman",error.toString());

                            }
                        });
                        queue.add(getRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("asd",e.getMessage());
                    }

                }
            }
        });
    }
}
