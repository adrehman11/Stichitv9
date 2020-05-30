package com.example.stichitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuhart.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetail extends AppCompatActivity {

    private ImageView back_btn_orderDetail;
    private StepView stepView;
    private Boolean first, second,third = false;
    private TextView dresstype,ordersid,ordertype,oderdeadline,phone_no,orderDate,dresstype_2,tailorname
            ,shir_details,trouser_details,sticthtype,lace,pipeing,buttons,comment,shirt_length,Shirt_neck,
            Shirt_chest,Shirt_waist,Shirt_backwidthck,
            Shirt_Hips,Shirt_sleeevelenght,Shirt_Shoulder
            ,Shirt_QuaterSleeveLength,Shirt_wrist,price,
            trouser_length,trouser_calf,trouser_ankle;
    private ImageView imageView;
    String orderID;
    String orderstatus,orderstatus2;
    final JSONObject post_data = new JSONObject();
    private RequestQueue queue;
    String urli = Config.url;
    String temp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        progressDialog = new ProgressDialog((OrderDetail.this));
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_loading_screen);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        Intent intent           = getIntent();
        orderID      = intent.getStringExtra("OID");

        queue= Volley.newRequestQueue(OrderDetail.this);

        back_btn_orderDetail =  findViewById(R.id.back_btn_orderDetail);

        back_btn_orderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Home_Tailor.class);
                startActivity(intent);
            }
        });
        stepView       =  findViewById(R.id.step_view);


        imageView =  findViewById(R.id.orderDetails_img5);

        shirt_length =  findViewById(R.id.orderDetails_Shirt_length);
        Shirt_neck = findViewById(R.id.orderDetails_Shirt_neck);
        Shirt_chest = findViewById(R.id.orderDetails_Shirt_chest);
        Shirt_waist = findViewById(R.id.orderDetails_Shirt_waist);
        Shirt_backwidthck = findViewById(R.id.orderDetails_Shirt_backwidth);
        Shirt_Hips = findViewById(R.id.orderDetails_Shirt_Hips);
        Shirt_sleeevelenght = findViewById(R.id.orderDetails_Shirt_sleeevelenght);
        Shirt_Shoulder = findViewById(R.id.orderDetails_Shirt_Shoulder);
        Shirt_QuaterSleeveLength = findViewById(R.id.orderDetails_Shirt_QuaterSleeveLength);
        Shirt_wrist = findViewById(R.id.orderDetails_Shirt_wrist);
        trouser_length = findViewById(R.id.orderDetails_trouser_length);
        trouser_calf = findViewById(R.id.orderDetails_trouser_calf);
        trouser_ankle = findViewById(R.id.orderDetails_trouser_ankle);

        dresstype =  findViewById(R.id.orderDetails_DressNameOnTop);
        ordersid = findViewById(R.id.orderDetails_DressID);

        oderdeadline = findViewById(R.id.cstomerneworder__expexteddate);
        tailorname = findViewById(R.id.orderDetails_CustomerName);
        phone_no = findViewById(R.id.textView18);

        dresstype_2 = findViewById(R.id.orderDetails_DressName);
        shir_details = findViewById(R.id.orderDetails_shirtdetails);
        trouser_details = findViewById(R.id.orderDetails_trouserdetails);
        sticthtype = findViewById(R.id.orderDetails_stitchType);
        lace = findViewById(R.id.orderDetails_lace);
        pipeing = findViewById(R.id.orderDetails_piping);
        buttons = findViewById(R.id.orderDetails_buttons);
        comment= findViewById(R.id.orderDetails_comments);
        price= findViewById(R.id.cstomerneworder__expextedprice);

        try {
            post_data.put("id",Home_Tailor.user_id);
            post_data.put("utype",Home_Tailor.utype);
            post_data.put("oid",orderID);
            temp = urli+"order/myorder/HistoryCurrent/details";
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    String message = null;

                    try {
                        message = response.getString("message");
                        if (message.equals("details")){


                            byte[] decodedString = Base64.decode(response.getString("image"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageView.setImageBitmap(decodedByte);


                            shirt_length.setText(response.getString("Shirt_length"));
                            Shirt_neck.setText(response.getString("Shirt_neck"));
                            Shirt_chest .setText(response.getString("Shirt_chest"));
                            Shirt_waist.setText(response.getString("Shirt_waist"));
                            Shirt_backwidthck .setText(response.getString("Shirt_backwidth"));
                            Shirt_Hips .setText(response.getString("Shirt_Hips"));
                            Shirt_sleeevelenght .setText(response.getString("Shirt_sleeevelenght"));
                            Shirt_Shoulder .setText(response.getString("Shirt_Shoulder"));
                            Shirt_QuaterSleeveLength .setText(response.getString("Shirt_QuaterSleeveLength"));
                            Shirt_wrist.setText(response.getString("Shirt_wrist"));
                            trouser_length .setText(response.getString("trouser_length"));
                            trouser_calf .setText(response.getString("trouser_calf"));
                            trouser_ankle.setText(response.getString("trouser_ankle"));

                            dresstype .setText(response.getString("dresstype"));
                            // Log.d("Rehman",response.getString("ordersID"));
                            ordersid .setText(response.getString("ordersID"));
                           // ordertype .setText(response.getString("odertype"));
                            oderdeadline .setText(response.getString("OderDeadline"));
                            tailorname .setText(response.getString("tailorname"));
                            phone_no.setText(response.getString("phoneno"));
//                            orderDate .setText(response.getString("orderDate"));
                            dresstype_2.setText(response.getString("dresstype"));
                            shir_details .setText(response.getString("shirtDetails"));
                            trouser_details .setText(response.getString("trouserDetails"));
                            sticthtype .setText(response.getString("stichtype"));
                            lace .setText(response.getString("lace"));
                            pipeing .setText(response.getString("pipe"));
                            buttons.setText(response.getString("button"));
                            comment.setText(response.getString("coments"));
                            price.setText(response.getString("Dressprice"));
                            orderstatus=response.getString("orderStatus");
                            if(orderstatus.equals("InProgress"))
                            {
                                stepView.go(0,true);
                            }
                            else if(orderstatus.equals("Cut")){
                                stepView.go(1,true);
                            }
                            else if(orderstatus.equals("Stitch")){
                                stepView.go(2,true);
                            }
                            else if(orderstatus.equals("Press")){
                                stepView.go(3,true);
                            }
                            progressDialog.dismiss();
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

        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .steps(new ArrayList<String>() {{
                    add("Start");
                    add("Cut");
                    add("Stitch");
                    add("Press");
                    add("Finish");
                }})
                .commit();




        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                if (step==1){
                    if(stepView.getCurrentStep()==0 || stepView.getCurrentStep()==1 || stepView.getCurrentStep()==2 || stepView.getCurrentStep()==3 ||stepView.getCurrentStep()==4)
                        stepView.go(1,true);
                    first = true;
                    orderstatus2 ="Cut";

                }
                if(step ==2){
                    if(stepView.getCurrentStep()==0 || stepView.getCurrentStep()==1 || stepView.getCurrentStep()==2 || stepView.getCurrentStep()==3 ||stepView.getCurrentStep()==4)
                        stepView.go(2,true);
                    second = true;
                    orderstatus2 ="Stitch";
                }
                if (step==3){

                    if(stepView.getCurrentStep()==0 || stepView.getCurrentStep()==1 || stepView.getCurrentStep()==2 || stepView.getCurrentStep()==3 ||stepView.getCurrentStep()==4)
                        stepView.go(3,true);
                    third = true;
                    orderstatus2 ="Press";
                }

                if (step==4){

                    if(stepView.getCurrentStep()==0 || stepView.getCurrentStep()==1 || stepView.getCurrentStep()==2 || stepView.getCurrentStep()==3 ||stepView.getCurrentStep()==4)
                        stepView.go(4,true);
                    third = true;
                    orderstatus2 ="Finish";
                }
                try {
                    post_data.put("id",Home_Tailor.user_id);
                    post_data.put("utype",Home_Tailor.utype);
                    post_data.put("oid",orderID);
                    post_data.put("orderstatus",orderstatus2);
                   String temp1 = urli+"order2/orderstatus";
                    JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp1, post_data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            String message = null;

                            try {
                                message = response.getString("message");
                                if (message.equals("done")){

                                    Toast.makeText(OrderDetail.this,"Order status has been changed",Toast.LENGTH_LONG).show();

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

    }

}
