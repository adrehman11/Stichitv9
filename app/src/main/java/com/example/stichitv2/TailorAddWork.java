package com.example.stichitv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TailorAddWork extends AppCompatActivity {

    private ImageView back_btn_addworkoftailor;
    private Button upload;
    private TextView description;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    private final int IMG_REQUEST =1;
    private Bitmap bitmap;
    ImageView imageView;
    String urli = Config.url;
    String temp  = urli+"test/tailorwork";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_add_work);

        queue= Volley.newRequestQueue(TailorAddWork.this);
        upload  =  findViewById(R.id.abc);
        back_btn_addworkoftailor  =  findViewById(R.id.back_btn_addwork);
        imageView = (ImageView)findViewById(R.id.imageview);

        description = findViewById(R.id.tf1);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });

        back_btn_addworkoftailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TailorAddWork.this,Profile.class);
                startActivity(intent);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String discription =description.getText().toString();
                Log.d("Rehman",discription);
                try {
                    post_data.put("id", Home_Tailor.user_id);
                    post_data.put("utype", Home_Tailor.utype);
                    post_data.put("image", imagetostring(bitmap));
                    post_data.put("description", discription);
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Rehman", "onresp" + response.toString());
                            String message = null;
                            try {
                                message = response.getString("message");
                                if(message.equals("work uploaded"))
                                {
                                    Intent intent = new Intent(TailorAddWork.this,Profile.class);
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

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                }
        });
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
    private String imagetostring(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        Log.d("asd", Base64.encodeToString(imgBytes,Base64.DEFAULT));
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
