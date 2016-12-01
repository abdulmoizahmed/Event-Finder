package com.appswallet.jamatfinder.Network;

import com.appswallet.jamatfinder.Models.Data;
import com.appswallet.jamatfinder.Models.NamazTime;
import com.appswallet.jamatfinder.firebase_push_notification.network.EndPoints;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by android on 11/30/16.
 */

public interface ApiInterface {

    @GET(EndNodes.Endpoint_URI)
    Call<NamazTime> getData(@Query("latitude") double lat,
                            @Query("longitude") double longitude,
                            @Query("timezonestring") String timezone,
                            @Query("method") int method,
                            @Query("school") int school);
}
