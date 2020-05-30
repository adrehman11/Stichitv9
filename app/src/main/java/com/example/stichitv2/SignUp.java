package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SignUp extends AppCompatActivity {


    private TextView    signin_link;
    private ImageView   backBtn;
    private Button signup_buttton;
    private EditText fname,lname,contact;
    private EditText email;
    private EditText password;
    private EditText cpassword;
    private Button Signup_btn;
    private RadioGroup User,Gender;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;

    String urli = Config.url;
    String temp  = urli+"signup";


    String Fname="";
    String Lname="";
    String Phone="";
    String Email="";
    String Password="";
    String Cpassword="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);

        //      TURNS OFF SOFT KEYBOARD FROM OPENING
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        //      DECLARATION OF  LAYOUT REFERENCE
        signin_link        = findViewById(R.id.signin_linkText);
        backBtn            = findViewById(R.id.back_signup);
        signup_buttton     = findViewById(R.id.signup_btn);
        queue= Volley.newRequestQueue(SignUp.this);
        fname = findViewById(R.id.signup_fname);
        lname = findViewById(R.id.signup_lname);
        contact = findViewById(R.id.signup_phoneNumber);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        cpassword = findViewById(R.id.signup_confirmpassword);
        User = findViewById(R.id.usertype_radioGroup_signup);
        Gender = findViewById(R.id.GenderRadioGroup);

        //        INTENT TO SIGNUP SCREEN
        signin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignIn.class);
                startActivity(intent);
            }
        });

        // BACK BUTTON LISTENER
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignIn.class);
                startActivity(intent);
            }
        });

        signup_buttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fname=fname.getText().toString();
                Lname = lname.getText().toString();
                Phone = contact.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                Cpassword = cpassword.getText().toString();
                RadioButton userType = findViewById(User.getCheckedRadioButtonId());
                RadioButton genderType = findViewById(Gender.getCheckedRadioButtonId());
                final String selectedGender = genderType.getText().toString();
                final String utype = userType.getText().toString();
                if(Fname.equals("")||Lname.equals("")||Phone.equals("")||Email.equals("")||password.equals("")||Cpassword.equals(""))
                {
                    Toast.makeText(SignUp.this,"All fields must be filled",Toast.LENGTH_LONG).show();
                }
                else if (!Password.equals(Cpassword))
                {
                    Toast.makeText(SignUp.this,"password does not match",Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        post_data.put("firstname", Fname);
                        post_data.put("lastname", Lname);
                        post_data.put("contact", Phone);
                        post_data.put("email", Email);
                        post_data.put("password", Password);
                        post_data.put("utype", utype);
                        post_data.put("gender", selectedGender);
                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("Rehman", "onresp" + response.toString());
                                String message = null;
                                String id = null;
                                String utype = null;
                                try {
                                    message = response.getString("message");
                                    if (message.equals("next")) {
                                        id = response.getString("id");
                                        utype = response.getString("utype");

                                       Intent i = new Intent(SignUp.this, TailorLocation.class);
                                        SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                        SharedPreferences.Editor editor =preferences.edit();
                                        editor.putString("utype",utype);
                                        editor.putString("id",id);
                                        editor.putString("rating",response.getString("rating"));
                                        editor.putString("name",response.getString("name"));
                                        editor.apply();
                                        startActivity(i);

                                    }
                                    if (message.equals("login")) {
                                        id = response.getString("id");
                                        Intent i = new Intent(SignUp.this, Home_Customer.class);
                                        SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                        SharedPreferences.Editor editor =preferences.edit();
                                        editor.putString("utype",utype);
                                        editor.putString("rating",response.getString("rating"));
                                        editor.putString("id",id);
                                        editor.putString("name",response.getString("name"));
                                        editor.apply();
                                        startActivity(i);
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
