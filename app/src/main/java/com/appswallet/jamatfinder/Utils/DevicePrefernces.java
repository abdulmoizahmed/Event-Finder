package com.appswallet.jamatfinder.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.appswallet.jamatfinder.Models.Timings;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by android on 11/30/16.
 */

public class DevicePrefernces {

    public static final String LOCATION_KEY = "location";
    public static final String preference = "mPrefernce";
    public static DevicePrefernces instance = null;
    public static SharedPreferences mPref;
    public Context context;


    private DevicePrefernces(){}

    public static DevicePrefernces getInstance()
    {
        if(instance == null)
        {
            instance = new DevicePrefernces();
        }

        return instance;
    }


    public void init(Context context)
    {
        this.context = context;
        mPref = context.getSharedPreferences(preference,context.MODE_PRIVATE);
    }

    public void saveLocation(){
        SharedPreferences.Editor editor;
        editor = mPref.edit();
        Gson gson = new Gson();

        String locationObject = gson.toJson(MyLocation.getInstance().getLocation());
        editor.putString(LOCATION_KEY,locationObject);
        editor.commit();
    }


    public void retrieveLocation()
    {
        Gson gson = new Gson();
        String mLocation;
        mLocation = mPref.getString(LOCATION_KEY,null);
        Location location = gson.fromJson(mLocation,Location.class);
        MyLocation.getInstance().setLocation(location);

    }


    public void saveNamazTime(Timings time)
    {

    }





    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void requestNamaz() {

        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Karachi);
        prayers.setAsrJuristic(prayers.Hanafi);
        prayers.setAdjustHighLats(prayers.AngleBased);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        ArrayList<String> prayerTime =  prayers.getPrayerTimes(cal,MyLocation.getInstance().getLocation().getLatitude(),MyLocation.getInstance().getLocation().getLongitude(),timezone);


        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);


    }

}
