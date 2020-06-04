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

public class EnterCode extends AppCompatActivity {

    private ImageView back_btn_entercode;
    private Button entercodebtn;
    private EditText entercodefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        final String code = preferences.getString("code","");
        back_btn_entercode = findViewById(R.id.back_btn_entercode);
        entercodebtn = findViewById(R.id.entercode_SubmitButton);
        entercodefield = findViewById(R.id.entercode_entercode);

        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_entercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ForgetPassword.class);
                startActivity(intent);
            }
        });
        entercodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usercode = entercodefield.getText().toString();
                Log.d("Rehman",code);
                Log.d("Rehman",usercode);
                if (code.equals(usercode) ) {
                    Toast.makeText(EnterCode.this, "Validation Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(),ResetPassword.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(EnterCode.this, "invalid Code", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
