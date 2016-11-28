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

    public static void getAndNotifyUser(final Context mContext){

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                    User fbUser = mdata.getValue(User.class);
//                    userRefTokensList.add(fbUser.getUserRefreshToken());
                    String token = fbUser.getUserRefreshToken();
                    pushTheUser(token,mContext);
                    Toast.makeText(mContext, "User Tokens ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, "Something wrong happened ! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void pushTheUser(String userRegToken, Context mContext) {
        Data data = new Data("First message to all users");
        PushData pushData = new PushData(data,userRegToken );
        SendPush.sendPushesToFbUsers(pushData, mContext);
    }
}
