package com.appswallet.jamatfinder.network;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Maaz on 11/22/2016.
 */
public class RestClient {

    private static ServiceInterface serviceInterface;

    private RestClient(){

    }

    static{

        setupRestClient();
    }

    public static void setupRestClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type","application/json")
                        .header("Authorization", "key= AIzaSyA-PbzZDTVJiBbzEcP85Ei0_VYCFv7QIBU"); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(EndPoints.BASE_URL)
                .client(client).build();

        serviceInterface = retrofit.create(ServiceInterface.class);
    }

    public static ServiceInterface getAdapter(){

        return serviceInterface;

    }




}
