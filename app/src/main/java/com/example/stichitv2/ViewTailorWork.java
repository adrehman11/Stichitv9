package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewTailorWork extends AppCompatActivity {

    private ImageView back_btn_viewworkoftailor,imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tailor_work);


         final Intent intent1 = getIntent();
       // String image = intent1.getStringExtra("image");
        final SharedPreferences preferences = getSharedPreferences("profile",MODE_PRIVATE);
        String image = preferences.getString("image","");
       String discription = intent1.getStringExtra("discription");
        final String screenname = intent1.getStringExtra("screen");

        back_btn_viewworkoftailor  =  findViewById(R.id.back_btn_workimg);
        imageView = findViewById(R.id.imageview);
        TextView description = findViewById(R.id.tf);

        description.setText(discription);

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        imageView.setVisibility(View.VISIBLE);

        back_btn_viewworkoftailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(screenname.equals("findtailorprofile")) {

                    Intent intent = new Intent(ViewTailorWork.this, FindTailorProfile.class);
                    intent.putExtra("tailorid",  intent1.getStringExtra("id"));
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(ViewTailorWork.this, Profile.class);
                    startActivity(intent);
                }
            }
        });
    }
}
