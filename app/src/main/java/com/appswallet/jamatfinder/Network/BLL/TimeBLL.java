package com.appswallet.jamatfinder.Network.BLL;

import android.widget.Toast;

import com.appswallet.jamatfinder.Models.NamazTime;
import com.appswallet.jamatfinder.Models.Timings;
import com.appswallet.jamatfinder.Network.ApiClient;
import com.appswallet.jamatfinder.Utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android on 11/30/16.
 */

public class TimeBLL {

    public static void getTiming(double lat,double lng,String timeZone)
    {
        Call<NamazTime> time = ApiClient.getAdapter().getData(lat,lng,timeZone,1,1);
        time.enqueue(new Callback<NamazTime>() {
            @Override
            public void onResponse(Call<NamazTime> call, Response<NamazTime> response) {
                if(response.isSuccessful())
                {


                    Timings time = new Timings();
                    time = response.body().getData().getTimings();
                    CommonUtils.getInstance().setTimes(time);

                }
            }

            @Override
            public void onFailure(Call<NamazTime> call, Throwable t) {

            }
        });

    }




}
