package com.appswallet.jamatfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.appswallet.jamatfinder.firebase_push_notification.main.MainCallin;
import com.appswallet.jamatfinder.firebase_push_notification.models.Data;
import com.appswallet.jamatfinder.firebase_push_notification.models.PushData;
import com.appswallet.jamatfinder.firebase_push_notification.models.User;
import com.appswallet.jamatfinder.firebase_push_notification.network.Bals.SendPush;
import com.appswallet.jamatfinder.firebase_push_notification.screens.LogInScreen;
import com.appswallet.jamatfinder.firebase_push_notification.utils.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button backBtn, getUsers;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference(FirebaseReferences.userRef);
    private List<String> userRefTokensList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backBtn = (Button)findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LogInScreen.class));
            }
        });

        getUsers = (Button)findViewById(R.id.getUserButton);
        getUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUsers_from_fbDatabase();
                MainCallin.getAndNotifyUser(getApplicationContext());
            }
        });
    }

/*-------------------- Methods for getting users and notify them --------------------------------------*/

    private void getUsers_from_fbDatabase() {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                    User fbUser = mdata.getValue(User.class);
                    String token = fbUser.getUserRefreshToken();
                    pushTheUser(token);
                    Toast.makeText(MainActivity.this, "User Tokens ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something wrong happened ! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pushTheUser(String userRegToken) {
        Data data = new Data("First message to all users");
        PushData pushData = new PushData(data,userRegToken );
        SendPush.sendPushesToFbUsers(pushData,getApplicationContext());
    }

//    public void sendPush(View view){
//
//        Data data = new Data("First message to all users");
//        for(int i=0; i<userRefTokensList.size(); i++) {
//            String userRegToken = userRefTokensList.get(i);
//            PushData pushData = new PushData(data,userRegToken );
//            SendPush.sendPushesToFbUsers(pushData,getApplicationContext());
//        }
//    }

    /*-------------------- End of Methods for getting users and notify them --------------------------------------*/
}
