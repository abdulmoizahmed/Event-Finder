package com.appswallet.jamatfinder.firebase_push_notification.network;

import com.appswallet.jamatfinder.firebase_push_notification.models.PushData;
import com.appswallet.jamatfinder.firebase_push_notification.models.PushResponse;
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
