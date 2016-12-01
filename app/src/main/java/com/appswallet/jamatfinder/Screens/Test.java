package com.appswallet.jamatfinder.Screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appswallet.jamatfinder.Models.Data;
import com.appswallet.jamatfinder.Models.NamazTime;
import com.appswallet.jamatfinder.Models.Timings;
import com.appswallet.jamatfinder.Network.ApiClient;
import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.Utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }


    public void startRequest(View view)
    {
        ApiClient.getAdapter().getData(24.8573473,67.0455685,"Asia/Karachi",1,1).enqueue(new Callback<NamazTime>()
        {
            @Override
            public void onResponse(Call<NamazTime> call, Response<NamazTime> response)
            {
                 if(response.isSuccessful())
                 {
                        Timings time = new Timings();
                            time = response.body().getData().getTimings();
                     CommonUtils.getInstance().setTimes(time);
                     Toast.makeText(getApplicationContext(),CommonUtils.getInstance().getTimes().getDhuhr(),Toast.LENGTH_SHORT);
                 }
            }

            @Override
            public void onFailure(Call<NamazTime> call, Throwable t) {

                                                                                     }
                                                                                 }

        );
    }
}
