package com.appswallet.jamatfinder.network.Bals;

import android.content.Context;
import android.widget.Toast;
import com.appswallet.jamatfinder.models.PushData;
import com.appswallet.jamatfinder.models.PushResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appswallet.jamatfinder.network.RestClient.getAdapter;

/**
 * Created by Maaz on 11/23/2016.
 */
public class SendPush {

    public static void sendPushesToFbUsers(PushData data, final Context context){

        Call<PushResponse> pushToUsers = getAdapter().sendPushToUsers(data);
        pushToUsers.enqueue(new Callback<PushResponse>() {
            @Override
            public void onResponse(Call<PushResponse> call, Response<PushResponse> response) {
                Toast.makeText(context, "Users have been notified !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushResponse> call, Throwable t) {
                Toast.makeText(context, "There is some problem !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
