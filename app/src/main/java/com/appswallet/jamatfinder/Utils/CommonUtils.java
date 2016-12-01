package com.appswallet.jamatfinder.Utils;

import android.content.Intent;
import android.content.SharedPreferences;

import com.appswallet.jamatfinder.Models.Timings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by android on 12/1/16.
 */
public class CommonUtils {
    public Timings times;
    private static CommonUtils Instance = null;


    private CommonUtils() {
    }

    public static CommonUtils getInstance()
    {
        if(Instance == null)
        {
            Instance = new CommonUtils();
        }
        return Instance;
    }


    public Timings getTimes() {
        return times;
    }

    public void setTimes(Timings times) {
        this.times = times;
    }



    public void fetchNamazTime()
    {
        double latitude = 24.857821475609732;
        double longitude = 67.04601734876633;
        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Karachi);
        prayers.setAsrJuristic(prayers.Hanafi);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                longitude, timezone);
        ArrayList prayerNames = prayers.getTimeNames();


                Timings time = new Timings();
                time.setFajr(prayerTimes.get(0).toString());
                time.setSunrise(prayerTimes.get(1).toString());
                time.setDhuhr(prayerTimes.get(2).toString());
                time.setAsr(prayerTimes.get(3).toString());
                time.setSunset(prayerTimes.get(4).toString());
                time.setMaghrib(prayerTimes.get(5).toString());
                time.setIsha(prayerTimes.get(6).toString());
                CommonUtils.getInstance().setTimes(time);

    }


}
