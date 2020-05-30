package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private ImageView back_btn_setting;
    private LinearLayout editprofile, price, language, helpandsupport, aboutus,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_setting =  findViewById(R.id.back_btn_setting);
        editprofile      = findViewById(R.id.edit_profile);
        price            = findViewById(R.id.set_price);
        language         = findViewById(R.id.language);
        helpandsupport   = findViewById(R.id.help_support);
        aboutus          = findViewById(R.id.about_us);
        logout           = findViewById(R.id.logout);

        Intent intent           = getIntent();
        final String screen_name      = intent.getStringExtra("screen_name");

        if (screen_name.equals("Customer"))
        {
            price.setVisibility(View.GONE);
        }
        else if ( screen_name.equals(("Tailor")))
        {
            price.setVisibility(View.VISIBLE);
        }

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);


        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (screen_name.equals("Tailor"))
                {
                    Intent intent = new Intent(view.getContext(),EditProfile.class);
                    intent.putExtra("screen_name","Tailor");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(view.getContext(),EditProfile.class);
                    intent.putExtra("screen_name","Customer");
                    startActivity(intent);
                }

            }
        });


        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Price.class);

                // EXTRA VALUE TO IDENTIFY INTENT
                intent.putExtra("screen_name","settings");

                startActivity(intent);
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Language.class);
                startActivity(intent);
            }
        });
        helpandsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HelpandSupport.class);
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AboutUs.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("rememberme",MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("key","false");
                editor.putString("utype","");
                editor.putString("id","");
                editor.apply();
                finish();
                Intent intent = new Intent(view.getContext(), SignIn.class);
                startActivity(intent);
            }
        });
    }
}