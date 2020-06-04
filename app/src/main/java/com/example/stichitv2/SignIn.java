package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.example.stichitv2.constant.CometChatConfig;

import org.json.JSONException;
import org.json.JSONObject;

import static android.text.TextUtils.isEmpty;
//import screen.unified.CometChatUnified;




public class SignIn extends AppCompatActivity {

    private TextView        signup_link, forgetpasswsord;
    private Button          signin_btn;
    private RadioGroup      user_type;
    private CheckBox checkbox;

    private EditText emailText,passwordText;
    private String selectedUser= "";

    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    private   boolean checked;

    String urli = Config.url;
    String temp  = urli+"login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        signup_link = findViewById(R.id.signup_linkText);
        signin_btn  = findViewById(R.id.signin_btn);
        user_type   = findViewById(R.id.usertype_radioGroup);
        forgetpasswsord = findViewById(R.id.signin_forgetPassword);


//Shared preferences
        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        String key = preferences.getString("key","");
        String RememberMeType= preferences.getString("utype","");
        if(key.equals("true")&& RememberMeType.equals("Customer"))
        {
            Intent i = new Intent(SignIn.this,Home_Customer.class);
            startActivity(i);
        }
        else if(key.equals("true")&& RememberMeType.equals("Tailor"))
        {
            Intent i = new Intent(SignIn.this,Home_Tailor.class);
            startActivity(i);
        }

        queue= Volley.newRequestQueue(SignIn.this);



        emailText = findViewById(R.id.username_input);
        passwordText =  findViewById(R.id.passowrd_input);
        checkbox=  findViewById(R.id.checkBox);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SignIn User into CometChatPro

//                if (CometChat.getLoggedInUser() == null) {
//                    CometChat.login("superhero5", CometChatConfig.authKey, new CometChat.CallbackListener<User>() {
//
//                        @Override
//                        public void onSuccess(User user) {
//                            Log.d(CometChatConfig.TAG, "Login Successful : " + user.toString());
//                        }
//
//                        @Override
//                        public void onError(CometChatException e) {
//                            Log.d(CometChatConfig.TAG, "Login failed with exception: " + e.getMessage());
//                        }
//                    });
//                } else {
//                    // User already logged in
//                    Toast.makeText(SignIn.this, "User is already logged in", Toast.LENGTH_SHORT).show();
//
//
//
//                    Intent intent =  new Intent(SignIn.this, CometChatUnified.class);
//                    intent.putExtra("userType","Customer");
//                    startActivity(intent);
//                }
//










                RadioButton  utype = findViewById(user_type.getCheckedRadioButtonId());
                final String utypec = utype.getText().toString();

                 checked = checkbox.isChecked();

                String email= emailText.getText().toString();
                String password = passwordText.getText().toString();
                try {
                    post_data.put("email",email);
                    post_data.put("password",password);
                    post_data.put("utype",utypec);
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            String id = null;
                            String utype = null;
                            String name = null;
                            String rating = null;
                            try {
                                message = response.getString("message");
                                Toast.makeText(SignIn.this,message,Toast.LENGTH_LONG).show();
                                if(message.equals("login confirm"))
                                {
                                    utype = response.getString("utype");
                                    id = response.getString("id");
                                    name = response.getString("name");
                                    rating = response.getString("rating");
                                    if(checked == true)
                                    {
                                        SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                        SharedPreferences.Editor editor =preferences.edit();
                                        editor.putString("key","true");
                                        editor.putString("utype",utype);
                                        editor.putString("id",id);
                                        editor.putString("rating",rating);
                                        editor.putString("name",name);
                                        editor.apply();
                                    }
                                    else if(checked ==false)
                                    {
                                        SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                        SharedPreferences.Editor editor =preferences.edit();
                                        editor.putString("key","false");
                                        editor.putString("utype",utype);
                                        editor.putString("id",id);
                                        editor.putString("rating",rating);
                                        editor.putString("name",name);
                                        editor.apply();
                                    }

                                    if (utype.equals("Customer"))
                                    {
                                        Intent i = new Intent(SignIn.this,Home_Customer.class);
                                        startActivity(i);
                                    }
                                    else if (utype.equals("Tailor")){
                                        Intent i = new Intent(SignIn.this,Home_Tailor.class);
                                        startActivity(i);
                                    }

                                }
                                else if(message.equals("wrong password"))
                                {
                                    Toast.makeText(SignIn.this,"wrong password",Toast.LENGTH_LONG).show();
                                    passwordText.setText("");
                                }
                                else if(message.equals("Email does not exsist"))
                                {
                                    emailText.setText("");
                                    passwordText.setText("");
                                    Toast.makeText(SignIn.this,"Email does not exsist",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("Rehman", "abc1");
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
                if(!isEmpty(email) && !isEmpty(password)){

                }
                else{
                    Toast.makeText(v.getContext(), "Enter email and passowrd", Toast.LENGTH_LONG).show();
                }


            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignUp.class);
                startActivity(intent);

            }
        });


        forgetpasswsord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ForgetPassword.class);
                startActivity(intent);

            }
        });

    }
}
