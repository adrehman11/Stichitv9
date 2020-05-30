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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {

    private ImageView back_btn_enteremail;
    private Button enterEmailbtn;
    private RadioGroup user;
    private EditText emailfield;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;

    String urli = Config.url;
    String temp  = urli+"forgetpassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        back_btn_enteremail = findViewById(R.id.back_btn_enterEmail);
        enterEmailbtn = findViewById(R.id.enterEmial_SubmitButton);

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        queue= Volley.newRequestQueue(ForgetPassword.this);

        emailfield = findViewById(R.id.enteremail_enterEmail);

        enterEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                user = findViewById(R.id.GenderRadioGroup);
                RadioButton userType = findViewById(user.getCheckedRadioButtonId());
                final String utype = userType.getText().toString();
                try {
                    post_data.put("email",emailfield.getText().toString());
                    post_data.put("utype",utype);
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            String id = null;
                            String utypec=null;
                            String code = null ;
                            try {
                                message = response.getString("message");
                                if (message.equals("done"))
                                {
                                    code = response.getString("code");
                                    utypec = response.getString("utype");
                                    id = response.getString("id");
                                    Intent i = new Intent(ForgetPassword.this,EnterCode.class);
                                    SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                    SharedPreferences.Editor editor =preferences.edit();
                                    editor.putString("utype",utypec);
                                    editor.putString("id",id);
                                    editor.putString("code",code);
                                    editor.apply();
                                    startActivity(i);
                                }
                                else if (message.equals("Email does not exsist"))
                                {
                                    Toast.makeText(ForgetPassword.this,"Email does not exsist",Toast.LENGTH_LONG).show();
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
        });
        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_enteremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SignIn.class);
                startActivity(intent);
            }
        });

    }
}
