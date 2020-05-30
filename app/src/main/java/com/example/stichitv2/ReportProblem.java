package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

public class ReportProblem extends AppCompatActivity implements IPickResult {

    private ImageView back_btn_reportproblme;
    private Button sendreq;
    private ImageView addimages,addimgetoreport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        //      DECLARATION OF  LAYOUT REFERENCE
        back_btn_reportproblme = findViewById(R.id.back_btn_reportproblem);
        sendreq                = findViewById(R.id.abc);
        addimages              = findViewById(R.id.addimgetoreport);
        addimgetoreport        = findViewById(R.id.addimgetoreport);


        //     NAVIGATE BACK TO HOME SCREEN
        back_btn_reportproblme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelpandSupport.class);
                startActivity(intent);
            }
        });

        sendreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelpandSupport.class);
                startActivity(intent);
            }
        });


        addimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(ReportProblem.this);

            }
        });


    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            addimgetoreport.setImageBitmap(r.getBitmap());
        } else {
            //Handle possible errors
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
