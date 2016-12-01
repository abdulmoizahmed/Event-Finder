package com.appswallet.jamatfinder.Network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by android on 11/30/16.
 */

public class ApiClient {
    public static ApiInterface mInterface;


    static {
        setupApiClient();
    }

    private static void setupApiClient() {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EndNodes.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpBuilder.build())
                .build();


        mInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getAdapter()
    {
        return mInterface;
    }
}
