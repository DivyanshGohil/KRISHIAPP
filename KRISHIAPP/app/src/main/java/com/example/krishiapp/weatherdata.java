package com.example.krishiapp;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class weatherdata {

    private String mTemperature,mCity,mweatherType;
    private int mCondition;



    public static weatherdata fromJson(JSONObject jsonObject)
    {

        try
        {
            weatherdata weatherD = new weatherdata();
            weatherD.mCity = jsonObject.getString("name");
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mweatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");

            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue = (int)Math.rint(tempResult);
            weatherD.mTemperature = Integer.toString(roundedValue);

            return weatherD;


        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public String getmCity() {
        return mCity;
    }

    public String getMweatherType() {
        return mweatherType;
    }
}
