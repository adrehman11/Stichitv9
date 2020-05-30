package com.example.stichitv2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

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

import org.json.JSONException;
import org.json.JSONObject;


public class TailorLocation extends FragmentActivity implements OnMapReadyCallback {


    android.location.Location mlocation;
    String urli = Config.url;
    String temp  = urli+"signup/tailorlocation";
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code=101;
    private RequestQueue queue;
    final JSONObject post_data = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        final SharedPreferences preferences = getSharedPreferences("rememberme",MODE_PRIVATE);
        final String id= preferences.getString("id","");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        queue= Volley.newRequestQueue(TailorLocation.this);

        GetlastLocation();

        //button code
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(selectedLocation == null){

                    Toast.makeText(TailorLocation.this, "Select your shop location", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Double lati = selectedLocation.getPosition().latitude ;
                    Double lngi =  selectedLocation.getPosition().longitude;

                    try {
                        post_data.put("lati",Double.toString(lati));
                        post_data.put("id",id);
                        post_data.put("lngi",Double.toString(lngi));
                        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, temp, post_data, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String message = null;
                                try {
                                    message = response.getString("message");
                                    Log.d("Rehman", "onresp" + response.toString());
                                    if(message.equals("location updated"))
                                    {

                                        Intent i = new Intent(TailorLocation.this,Price.class);
                                        i.putExtra("screen_name","afterlocation");
                                        Toast.makeText(TailorLocation.this, "location set", Toast.LENGTH_SHORT).show();
                                        startActivity(i);

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

                }
            }
        });
    }
    private void GetlastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_Code);
            return;
        }

        Task<android.location.Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if(location != null)
                {
                    mlocation=location;
                    SupportMapFragment supportMapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
                    supportMapFragment.getMapAsync((OnMapReadyCallback) TailorLocation.this);
                }

            }
        });

    }
    //selected location
    Marker selectedLocation;
    public void onMapReady(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("location is yours");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        googleMap.addMarker(markerOptions);

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(selectedLocation == null) {
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("This location.").draggable(true);
                    selectedLocation = googleMap.addMarker(markerOptions);
                }else{
                    selectedLocation.setPosition(latLng);
                }

            }
        });
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case(Request_Code):
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    GetlastLocation();
                }
                break;
        }
    }
}
