package com.appswallet.jamatfinder.network;

import com.appswallet.jamatfinder.models.PushData;
import com.appswallet.jamatfinder.models.PushResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Maaz on 11/23/2016.
 */
public interface ServiceInterface {

    @POST(EndPoints.SEND_PUSH)
    Call<PushResponse> sendPushToUsers(@Body PushData pushData);
}
