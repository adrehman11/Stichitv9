package com.example.stichitv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.ArrayList;

public class FindTailor extends AppCompatActivity implements OnMapReadyCallback {
    Location mlocation;
    ArrayList<Tailor> tailors;
    String lati,lngi;
    double lati1,lngi1;
    String urli = Config.url;
    String temp = urli + "maplocation";
    public static String ID="";
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;
    private RequestQueue queue;
    final JSONObject post_data = new JSONObject();
    GoogleMap ggoogleMap=null;
    Dialog myDialog;
    String  screen,orderID,orderDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tailor_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        queue = Volley.newRequestQueue(FindTailor.this);
        GetlastLocation();
        Intent intent           = getIntent();
        screen =intent.getStringExtra("screen");
        if(screen.equals("reorder"))
        {
         orderDate=   intent.getStringExtra("orderdate");
            orderID=   intent.getStringExtra("orderID");
            Log.d("Rehman",orderID);
        }

        tailors = new ArrayList<Tailor>();
        myDialog = new Dialog(this);
    }


    public  void onMapLoaded(){

    }
    private void GetlastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mlocation = location;
                    lati1= mlocation.getLatitude();
                    lngi1 = mlocation.getLongitude();
                    lati= String.valueOf(lati1);
                    lngi= String.valueOf(lngi1);
                    Log.d("Rehman",lati);
                    Log.d("Rehman",lngi);
                    try {
                        post_data.put("id",Home_Customer.user_id);
                        post_data.put("utype",Home_Customer.utype);
                        post_data.put("lati",lati);
                        post_data.put("lngi",lngi);
                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("Rehman", "onresp" + response.toString());

                                try {
                                    JSONArray array = response.getJSONArray("resData");
                                    for(int i=0;i<array.length();i++)
                                    {
                                        JSONObject resdata = array.getJSONObject(i);
                                        String id = resdata.getString("tailor_ID");
                                        String lati = resdata.getString("tailor_lati");
                                        String longi = resdata.getString("tailor_lngi");
                                        String name = "";
                                        Tailor temp_t = new Tailor(id, name, lati, longi);
                                        tailors.add(temp_t);

                                    }
                                    while(ggoogleMap==null);
                                    for(int i=0;i<tailors.size();i++) {
                                        float lati1 = Float.parseFloat(tailors.get(i).lati);
                                        float lngi1= Float.parseFloat(tailors.get(i).longi);
                                        LatLng latLng1 = new LatLng(lati1,lngi1);
                                        MarkerOptions markerOptions0 = new MarkerOptions().position(latLng1).title("location 0 tailor");
                                        ggoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
                                        ggoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13));
                                        ggoogleMap.addMarker(markerOptions0);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
                    supportMapFragment.getMapAsync((OnMapReadyCallback) FindTailor.this);
                }

            }
        });
    }

    Marker selectedLocation;
    public void onMapReady(GoogleMap googleMap) {
        ggoogleMap=googleMap;
        LatLng latLng = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("location is yours");
        ggoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        ggoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        ggoogleMap.addMarker(markerOptions);

        ggoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String lat=  ""+marker.getPosition().latitude;
                lat = lat.substring(0,7);
                String lang= ""+marker.getPosition().longitude;
                lang = lang.substring(0,7);

                for (Tailor tailor:tailors) {
                    ID=tailor.id;
                    String lati1=tailor.lati.substring(0,7);
                    String lngi1=tailor.longi.substring(0,7);
                    if(lati1.equals(lat)&& lngi1.equals(lang))
                    {
                        ShowPopup(ID);

                    }

                }


                return false;

            }
        });

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case(Request_Code):
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    GetlastLocation();
                }
                break;
        }
    }
    public void ShowPopup(final String id) {
        final TextView txtclose,Name,Address,Contact,Gender;
        Button btnFollow;
        final ImageView Image;
        myDialog.setContentView(R.layout.activity_popup_profile);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        Name = (TextView) myDialog.findViewById(R.id.Name_tailor);
        Address = (TextView) myDialog.findViewById(R.id.address_tailor);
        Contact = (TextView) myDialog.findViewById(R.id.Contact_tailor);
        Gender = (TextView) myDialog.findViewById(R.id.Gender_tailor);
        btnFollow = (Button) myDialog.findViewById(R.id.btnviewprofile);
        Image = (ImageView) myDialog.findViewById(R.id.profile_image);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindTailor.this, FindTailorProfile.class);
                intent.putExtra("tailorid",id);
                if(screen.equals("reorder"))
                {
                    intent.putExtra("orderdate",orderDate);
                    intent.putExtra("orderID",orderID);
                }
                intent.putExtra("screen",screen);
                startActivity(intent);
            }
        });
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        try {
            post_data.put("id",id);
            post_data.put("utype","Tailor");
            String temp1= Config.url+"maplocation/tailorpopup";
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp1, post_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("Rehman", "onresp" + response.toString());
                    String name =null;
                    String contact =null;
                    String image =null;
                    String address=null;
                   String  gender=null;
                    try {
                        if(response.getString("messeage").equals("popup")){
                            name = response.getString("tailorName");
                            contact = response.getString("contact");
                            gender = response.getString("gender");
                            image = response.getString("image");
                            address = response.getString("tailorLocation");
                            Name.setText(name);
                            Address.setText(address);
                            Contact.setText(contact);
                            Gender.setText(gender);
                            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Image.setImageBitmap(decodedByte);


                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
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
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
