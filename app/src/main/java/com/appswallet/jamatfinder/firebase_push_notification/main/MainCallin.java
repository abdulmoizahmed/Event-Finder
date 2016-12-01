package com.appswallet.jamatfinder.firebase_push_notification.main;

import android.content.Context;
import android.widget.Toast;
import com.appswallet.jamatfinder.firebase_push_notification.models.Data;
import com.appswallet.jamatfinder.firebase_push_notification.models.PushData;
import com.appswallet.jamatfinder.firebase_push_notification.models.User;
import com.appswallet.jamatfinder.firebase_push_notification.network.Bals.SendPush;
import com.appswallet.jamatfinder.firebase_push_notification.utils.FirebaseReferences;
import com.google.firebase.database.*;

/**
 * Created by Maaz on 11/28/2016.
 */
public class MainCallin {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference userRef = database.getReference(FirebaseReferences.userRef);


    public static void getAndNotifyUser(final Context mContext, final String message, final double latitude, final double longitude){

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                    User fbUser = mdata.getValue(User.class);
                    String token = fbUser.getUserRefreshToken();
                    Toast.makeText(mContext, "User Tokens: "+token, Toast.LENGTH_SHORT).show();
                    pushTheUser(token,mContext,message,latitude,longitude);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, "Something wrong happened ! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void pushTheUser(String userRegToken, Context mContext, String message, double latitude, double longitude) {
        Data data = new Data(message);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        PushData pushData = new PushData(data,userRegToken );
        SendPush.sendPushesToFbUsers(pushData, mContext);
    }
}
