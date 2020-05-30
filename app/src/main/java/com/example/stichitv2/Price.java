package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Price extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private ImageView back_btn_price;
    private Button price_savebutton,price_skipbutton,addMoreDress_women,addMoreDress_men;

private Boolean val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11;
    private ArrayList<String> DressName = new ArrayList<>(0);
    private ArrayList<String> DressPrice = new ArrayList<>(0);


    private LinearLayout women_linearLayout,men_linearLayout;

    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp  = urli+"signup/tailorprice";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_price      =  findViewById(R.id.back_btn_price);
        price_skipbutton    =  findViewById(R.id.price_skipbutton);
        price_savebutton    =  findViewById(R.id.price_savebutton);
        addMoreDress_women  =  findViewById(R.id.addMoreDress_women);
        women_linearLayout  =  findViewById(R.id.women_linearLayout);
        addMoreDress_men    =  findViewById(R.id.addMoreDress_men);
        men_linearLayout    =  findViewById(R.id.men_linearLayout);
val1=false;
val2=false;
val3=false;
val4=false;
val5=false;
val6=false;
val7=false;
val8=false;
val9 =false;
        val10 =false;
        val11 =false;
        //shared preference

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        final String id= preferences.getString("id","");

        // RECIEVE SCREEN NAME
        Intent intent           = getIntent();
        String screen_name      = intent.getStringExtra("screen_name");

        if(screen_name.equals("settings")){
            price_skipbutton.setVisibility(View.GONE);
            back_btn_price.setVisibility(View.VISIBLE);

        }
        else{
            price_skipbutton.setVisibility(View.VISIBLE);
            back_btn_price.setVisibility(View.GONE);
        }

        queue= Volley.newRequestQueue(Price.this);

        final  Spinner Shalwar_Kameez = (Spinner) findViewById(R.id.Women_ShalwarKameez);
        final Spinner Frock = (Spinner) findViewById(R.id.Women_Frock);
        final Spinner Peshwas = (Spinner) findViewById(R.id.Women_peshwas);
        final Spinner Saree = (Spinner) findViewById(R.id.Women_saree);
        final Spinner lahenga = (Spinner) findViewById(R.id.Women_lehenga);
        final Spinner Kurti = (Spinner) findViewById(R.id.Women_Kurti);
        final Spinner Men_Shalwar_Kameez = (Spinner) findViewById(R.id.Men_shalwarKameez);
        final Spinner Kurta = (Spinner) findViewById(R.id.Men_kurta);
        final Spinner Sherwani = (Spinner) findViewById(R.id.Men_sherwani);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.price, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Shalwar_Kameez.setAdapter(adapter);
        Frock.setAdapter(adapter);
        Peshwas.setAdapter(adapter);
        Saree.setAdapter(adapter);
        lahenga.setAdapter(adapter);
        Kurti.setAdapter(adapter);
        Men_Shalwar_Kameez.setAdapter(adapter);
        Kurta.setAdapter(adapter);
        Sherwani.setAdapter(adapter);


        Shalwar_Kameez.setOnItemSelectedListener(this);

        Frock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Frock.getId() && val1==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Frock");
                }
                else{
                    val1=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Peshwas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Peshwas.getId()&& val2==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Peshwas");
                } else{
                    val2=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Saree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Saree.getId()&& val3==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Saree");
                } else{
                    val3=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lahenga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==lahenga.getId()&& val4==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("lahenga");
                } else{
                    val4=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Kurti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Kurti.getId()&& val5==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Kurti");
                } else{
                    val5=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Men_Shalwar_Kameez.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Men_Shalwar_Kameez.getId()&& val6==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Men_Shalwar_Kameez");
                } else{
                    val6=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Kurta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Kurta.getId()&& val7==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Kurta");
                } else{
                    val7=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Sherwani.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Sherwani.getId()&& val8==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add("Sherwani");
                } else{
                    val8=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        price_savebutton = findViewById(R.id.price_savebutton);
        price_skipbutton = findViewById(R.id.price_skipbutton);


        price_savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    post_data.put("DressName",DressName);
                    post_data.put("DressPrice",DressPrice);
                    post_data.put("id",id);
                    post_data.put("utype","Tailor");
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;

                            try {
                                message = response.getString("message");
                                if (message.equals("price added to your profile"))
                                {
                                    Toast.makeText(Price.this,message,Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                                    SharedPreferences.Editor editor =preferences.edit();
                                    editor.putString("id",response.getString("id"));
                                    editor.putString("utype",response.getString("utype"));
                                    editor.putString("name",response.getString("name"));
                                    editor.apply();
                                    Intent i = new Intent(Price.this,Home_Tailor.class);
                                    startActivity(i);
                                }
                                else if (message.equals("error"))
                                {
                                    Toast.makeText(Price.this,message,Toast.LENGTH_LONG).show();
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
        price_skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();

                editor.putString("name","Welcome Back");
                editor.apply();
                Intent i = new Intent(Price.this,Home_Tailor.class);
                startActivity(i);
            }
        });

        back_btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Settings.class);
                startActivity(intent);
            }
        });


        // ADD DRESS LISTENER

        addMoreDress_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDressWomen();
            }
        });

        addMoreDress_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDressMen();
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        if(val9==true)
        {
        DressPrice.add(parent.getItemAtPosition(i).toString()) ;
        DressName.add("Shalwar_Kameez");

        }
        else
        {
            val9=true;
        }
        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    public void addDressWomen(){

        LinearLayout  newAddDress   = new LinearLayout(this);
        final Spinner       prices_women  = new Spinner(this);
        final EditText      women_dress_name    = new EditText(this);

        LinearLayout.LayoutParams params            = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params_edittext   =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        LinearLayout.LayoutParams params_spinner    =  new LinearLayout.LayoutParams(220,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);


        params_spinner.gravity = Gravity.RIGHT;
        params.gravity         = Gravity.RIGHT;
        params.topMargin       = 15;


        newAddDress.setLayoutParams(params);
        women_dress_name.setLayoutParams(params_edittext);
        prices_women.setLayoutParams(params_spinner);


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.price));

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        women_dress_name.setHint("Dress Name");
        prices_women.setAdapter(spinnerArrayAdapter);



        newAddDress.addView(women_dress_name);
        newAddDress.addView(prices_women);

        women_linearLayout.addView(newAddDress);
        prices_women.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==prices_women.getId()&& val10 ==true){
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add(women_dress_name.getText().toString());

                }
                else
                {
                    val10 =true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    public void addDressMen(){

        LinearLayout  newAddDress   = new LinearLayout(this);
        final EditText      Men_dress_name    = new EditText(this);
        final Spinner       prices_men  = new Spinner(this);
        final boolean val10 = false;
        LinearLayout.LayoutParams params            = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params_edittext   =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
        LinearLayout.LayoutParams params_spinner    =  new LinearLayout.LayoutParams(220,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);


        params_spinner.gravity = Gravity.RIGHT;
        params.gravity         = Gravity.RIGHT;
        params.topMargin       = 15;


        newAddDress.setLayoutParams(params);
        Men_dress_name.setLayoutParams(params_edittext);
        prices_men.setLayoutParams(params_spinner);


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.price));

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Men_dress_name.setHint("Dress Name");
        prices_men.setAdapter(spinnerArrayAdapter);

        newAddDress.addView(Men_dress_name);
        newAddDress.addView(prices_men);

        men_linearLayout.addView(newAddDress);
        prices_men.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==prices_men.getId()&& val11==true){
                   // Log.d("Rehman",)
                    DressPrice.add(adapterView.getItemAtPosition(i).toString());
                    DressName.add(Men_dress_name.getText().toString());

                }
                else
                {
                    val11=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }





}
