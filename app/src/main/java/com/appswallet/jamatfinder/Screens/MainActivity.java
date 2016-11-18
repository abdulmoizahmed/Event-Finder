package com.appswallet.jamatfinder.Screens;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appswallet.jamatfinder.FireBase.EventModel;
import com.appswallet.jamatfinder.FireBase.FirebaseReferences;
import com.appswallet.jamatfinder.FireBase.MasjidModel;
import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.Utils.Const;
import com.appswallet.jamatfinder.Utils.MyLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.appswallet.jamatfinder.R.id.map;
import static com.google.android.gms.location.LocationRequest.create;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener {
    String key;
    private GoogleMap mMap;
    private FloatingActionButton actionButton;
    private SupportMapFragment mapFragment;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = database.getReference(FirebaseReferences.eventRef);
    private DatabaseReference masjidRef = database.getReference(FirebaseReferences.masjidRef);
    private ArrayList<EventModel> jamatlist;
    private ArrayList<MasjidModel> masjidlist;
    private GoogleApiClient apiClient;
    private LocationRequest request;
    private Location mLocation;

    int toogle = 0;

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiClient.disconnect();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGoogleClient();
        initView();
        startListener();


    }

    private void initGoogleClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();


        }
        apiClient.connect();
        createLocationRequest();
    }

    private void createLocationRequest() {
        request = create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5 * 1000);
    }


    private void startListener() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Addjamat.class));
            }
        });

    }

    private void initView() {
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
        jamatlist = new ArrayList<EventModel>();
        masjidlist = new ArrayList<MasjidModel>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;





        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            Const.MY_PERMISSIONS_REQUEST_READ_LOCATION);
                    return;
                }
        else
        {
            mMap.setMyLocationEnabled(true);
            getAllEvents();
            mMap.setOnMarkerClickListener(this);


        }





    }

    private void getAllEvents() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                        EventModel model = mdata.getValue(EventModel.class);
                        jamatlist.add(model);
                    }

                }
                createMArkers(jamatlist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        masjidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot mdata : dataSnapshot.getChildren()) {
                        MasjidModel model = mdata.getValue(MasjidModel.class);
                        masjidlist.add(model);
                    }

                }

                createMasjid(masjidlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createMasjid(ArrayList<MasjidModel> masjidlist) {
        for(MasjidModel masjid : masjidlist)
        {
            LatLng location = new LatLng(masjid.getLatitude(), masjid.getLongitude());
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.masjid)).position(location).title(masjid.getMasjidName())).setTag(masjid);
        }
    }


    private void createMArkers(ArrayList<EventModel> eventModels) {
        for (EventModel jamat : eventModels) {
            LatLng location = new LatLng(jamat.getLatitude(), jamat.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title(jamat.getEventName()+" "+jamat.getEventTime()).snippet(jamat.getEventlocationName())).setTag(jamat);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Const.MY_PERMISSIONS_REQUEST_READ_LOCATION);

            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, request, this);
        mLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        MyLocation.getInstance().setLocation(mLocation);
        if (mLocation != null)
        {
            addMarker();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(),mLocation.getLongitude()), 18.0f));

        }

    }

    private void addMarker() {
        LatLng latLng = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
            mLocation = location;
            MyLocation.getInstance().setLocation(mLocation);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

       EventModel model = (EventModel) marker.getTag();
        if(model!=null) {
            createDialog(model);
      }
        else
        {
            Toast.makeText(getApplicationContext(),"This is your Location", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void createDialog(EventModel model) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Jamat Detail")
                .content("Jamat : "+model.getEventName()+"\n"+
                          "Time : "+model.getEventTime()+"\n"+
                          "Location : "+model.getEventlocationName())

                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Const.MY_PERMISSIONS_REQUEST_READ_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MyLocation.getInstance().setAccessPermission(true);
            }
            {
                MyLocation.getInstance().setAccessPermission(false);
                Toast.makeText(getApplicationContext(),"The permission is not granted",Toast.LENGTH_SHORT);
            }
        }

    }
}
