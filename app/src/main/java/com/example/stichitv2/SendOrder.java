package com.example.stichitv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SendOrder extends AppCompatActivity {

    private ImageView back_btn_sendorder;
    private Button sendorderbtn;
    private Bitmap bitmap;
    private ImageView addimages;
    private TextView  editmeaurement;
    private EditText shirtDetails,trouserDetails,comments;
    private RadioGroup Stichtype,Lace,Piping,Buttons,oderdate;
    String DressType= "";
    private RequestQueue queue;
    private final int IMG_REQUEST =1;
    ImageView imageView;
    private CalendarView calender;
    long millis;
    String OderDates= "";
     String tailorid;
    String DressPrice;
    JSONArray Dressprice;
    ProgressDialog progressDialog;
    private ArrayList<String> DressName = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        //date of system
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        try
        {

            Date curDate = new Date();
            millis = curDate.getTime();

        }
        catch (Exception e)
        {
            Log.d("Rehman","date ni chali");
        }
        calender = findViewById(R.id.calnderview);

        calender.setDate(millis);
        calender.setMinDate(millis);


        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                OderDates= (i1+1)+"/"+i2+"/"+i;
            }
        });

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_sendorder = findViewById(R.id.back_btn_sendOrder);
        sendorderbtn = findViewById(R.id.sendOrder_sendButton);
        addimages = findViewById(R.id.addDressimages);
        editmeaurement = findViewById(R.id.sendOrder_editmeasurementstextView);



        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_sendorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SendOrder.class);
                startActivity(intent);
            }
        });
        final JSONObject post_data = new JSONObject();
        String urli = Config.url;
        final String temp  = urli+"order/requirement";
        queue= Volley.newRequestQueue(SendOrder.this);



        final Spinner Dress = (Spinner) findViewById(R.id.dresstype);
        final ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,DressName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Dress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getId()==Dress.getId()){
                    DressType= adapterView.getItemAtPosition(i).toString();

                    try{DressPrice= Dressprice.getString(i);}
                    catch (JSONException e) {
                        Toast.makeText(SendOrder.this,"price not set",Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shirtDetails = findViewById(R.id.sendOrder_shirtDetails);
        trouserDetails = findViewById(R.id.sendOrder_trouserDetails);
        comments = findViewById(R.id.sendOrder_comments);

        Stichtype = findViewById(R.id.sendOrder_stitchtype_radioGroup);

        Lace = findViewById(R.id.sendOrder_lace_radioGroup);


        Piping = findViewById(R.id.sendOrder_Piping_radioGroup);


        Buttons = findViewById(R.id.sendOrder_buttons_radioGroup);


        oderdate = findViewById(R.id.sendOrder_delivery_radioGroup);

        imageView = findViewById(R.id.addDressimages);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });

        Intent intent           = getIntent();
        final String screen      = intent.getStringExtra("screen");
        if(screen.equals("2"))
        {
            SharedPreferences sharedPref = getSharedPreferences("sendorder",MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPref.edit();
            String tailorids = sharedPref.getString("tailorid","");
            String Shirtdetails = sharedPref.getString("Shirtdetails","");
            String Trouserdetails = sharedPref.getString("Trouserdetails","");
            String stich = sharedPref.getString("stich","");
            String laces = sharedPref.getString("laces","");
            String pipe = sharedPref.getString("pipe","");
            String button = sharedPref.getString("button","");
            String oders = sharedPref.getString("oders","");
            String image = sharedPref.getString("image", "");
            String orderdate = sharedPref.getString("orderdate","");
            String commentss = sharedPref.getString("comments","");
            String dresstype = sharedPref.getString("dresstype","");

            tailorid=tailorids;
            shirtDetails.setText(Shirtdetails);
            trouserDetails.setText(Trouserdetails);
            comments.setText(commentss);
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
            DressType=dresstype;




        }
        if (screen.equals("1"))
        {
            tailorid      = intent.getStringExtra("tailorid");
        }


        try {
            post_data.put("id",Home_Customer.user_id);
            post_data.put("tailorid",tailorid);
            post_data.put("utype",Home_Customer.utype);
            final String temp1  = urli+"test/GetDressName";
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp1, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Rehman",response.toString());

                    try {
                        JSONArray Dressarray = response.getJSONArray("DressName");
                        Dressprice = response.getJSONArray("DressPrice");
                        for(int i=0;i<Dressarray.length();i++)
                        {
                            DressName.add(Dressarray.getString(i));
                        }

                            Dress.setAdapter(adapter);


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
            Log.d("Rehman",e.getMessage());
        }

        editmeaurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Shirtdetails = shirtDetails.getText().toString();
                String Trouserdetails = trouserDetails.getText().toString();
                String Comments = comments.getText().toString();

                RadioButton sticht = findViewById(Stichtype.getCheckedRadioButtonId());
                final String stich = sticht.getText().toString();

                RadioButton lace = findViewById(Lace.getCheckedRadioButtonId());
                final String laces = lace.getText().toString();

                RadioButton piping = findViewById(Piping.getCheckedRadioButtonId());
                final String pipe = piping.getText().toString();

                RadioButton buttons = findViewById(Buttons.getCheckedRadioButtonId());
                final String button = buttons.getText().toString();

                RadioButton oder = findViewById(oderdate.getCheckedRadioButtonId());
                final String oders = oder.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("sendorder",MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPref.edit();
                editor.putString("tailorid",tailorid);
                editor.putString("Shirtdetails",Shirtdetails);
                editor.putString("Trouserdetails",Trouserdetails);
                editor.putString("dresstype",DressType);
                editor.putString("stich",stich);
                editor.putString("laces",laces);
                editor.putString("pipe",pipe);
                editor.putString("button",button);
                editor.putString("oders",oders);
                editor.putString("image", imagetostring(bitmap));
                editor.putString("orderdate",OderDates);
                editor.putString("comments",Comments);
                editor.apply();
                Intent intent = new Intent(view.getContext(),Measurements.class);
                intent.putExtra("screen","sendorder");
                startActivity(intent);

            }
        });

        sendorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog((SendOrder.this));
                progressDialog.show();
                progressDialog.setContentView(R.layout.activity_loading_screen);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                String Shirtdetails = shirtDetails.getText().toString();
                String Trouserdetails = trouserDetails.getText().toString();
                String Comments = comments.getText().toString();

                RadioButton sticht = findViewById(Stichtype.getCheckedRadioButtonId());
                final String stich = sticht.getText().toString();

                RadioButton lace = findViewById(Lace.getCheckedRadioButtonId());
                final String laces = lace.getText().toString();

                RadioButton piping = findViewById(Piping.getCheckedRadioButtonId());
                final String pipe = piping.getText().toString();

                RadioButton buttons = findViewById(Buttons.getCheckedRadioButtonId());
                final String button = buttons.getText().toString();

                RadioButton oder = findViewById(oderdate.getCheckedRadioButtonId());
                final String oders = oder.getText().toString();



                try {
                    post_data.put("id",Home_Customer.user_id);
                    post_data.put("tailorid",tailorid);
                    post_data.put("utype",Home_Customer.utype);
                    post_data.put("shirtdetails",Shirtdetails);
                    post_data.put("trouserdetails",Trouserdetails);
                    post_data.put("dresstype",DressType);
                    post_data.put("dressprice",DressPrice);
                    post_data.put("stichtype",stich);
                    post_data.put("lace",laces);
                    post_data.put("pipe",pipe);
                    post_data.put("button",button);
                    post_data.put("odertype",oders);
                    post_data.put("image", imagetostring(bitmap));
                    post_data.put("orderdate", OderDates);
                    post_data.put("comments", Comments);



                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = null;
                            try {
                                message = response.getString("message");
                                Log.d("Rehman",response.toString());
                                if(message.equals("order placed"))
                                {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(SendOrder.this,Home_Customer.class);
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
                    Log.d("Rehman",e.getMessage());
                }
            }
        });

    }
    private String imagetostring(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        Log.d("asd", Base64.encodeToString(imgBytes,Base64.DEFAULT));
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    private void selectimage()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
