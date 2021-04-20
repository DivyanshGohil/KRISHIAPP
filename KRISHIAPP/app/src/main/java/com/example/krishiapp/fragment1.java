package com.example.krishiapp;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment1 extends Fragment {

    final String APP_ID = "c102f514cb0a94f5d02c433990688b9f";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;//5 sec
    final float MIN_DISTANCE = 1000;// 1 meter
    final int REQUEST_CODE = 101;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView Temperature,WeatherCondition,City,Lat,Lon,desc;
    RelativeLayout outbutton;


    //Imagevview


    LocationManager mLocationManager;
    LocationListener mLocationListerner;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment1 newInstance(String param1, String param2) {
        fragment1 fragment = new fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment1, container, false);

        Temperature = v.findViewById(R.id.temperature);
        WeatherCondition = v.findViewById(R.id.weatherCondition);
        City = v.findViewById(R.id.cityName);
        Lat = v.findViewById(R.id.latitude);
        Lon = v.findViewById(R.id.longitude);
        desc = v.findViewById(R.id.description);
        outbutton = v.findViewById(R.id.output);


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getContext());


        outbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, "https://mocki.io/v1/6563038b-c0a3-4775-a094-7c00986daf71"
                        , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String lon = response.getJSONObject("coord").getString("lon");
                            String lan = response.getJSONObject("coord").getString("lat");
                            String city = response.getString("name");
                            Log.d("myapp",city);
                            double te = response.getJSONObject("main").getDouble("temp")-273.15;
                            String temp = String.valueOf(te);
                            String subtemp = temp.substring(0,6);

                            String wc = response.getJSONArray("weather").getJSONObject(0).getString("main");
                            String des = response.getJSONArray("weather").getJSONObject(0).getString("description");

                            desc.setText(des);
                            Temperature.setText(subtemp +"°C");
                            WeatherCondition.setText(wc);
                            City.append(city);
                            Lat.append(lan);
                            Lon.append(lon);

                            Log.d("myapp",subtemp);
                            Log.d("myapp",wc);
                            Log.d("myapp",city);
                            Log.d("myapp",lan);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("myapp","Something went wrong");
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });



        return v;
    }

    @Override
      public void onResume() {
        super.onResume();
        getWeatherForCurrentLocation();
    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationListerner = location -> {

            String Latitude = String.valueOf(location.getLatitude());
            String Longitude = String.valueOf(location.getLongitude());
            Log.d("myapp",Latitude);
            Log.d("myapp",Longitude);



            String url = "http://api.openweathermap.org/data/2.5/weather?lat=" +Latitude+ "&lon=" +Longitude+ "&appid=" +APP_ID;


        };

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return ;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListerner);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.d("myapp","get");
                Toast.makeText(getContext(),"LOCATION GET SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                //getWeatherForCurrentLocation();
            }
            else
            {
                Log.d("myapp","Not get");
                Toast.makeText(getContext(),"LOCATION GET DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }






    @Override
    public void onPause() {
        super.onPause();

        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListerner);
        }
    }
}

