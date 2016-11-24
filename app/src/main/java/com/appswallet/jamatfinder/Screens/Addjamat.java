package com.appswallet.jamatfinder.Screens;


import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appswallet.jamatfinder.FireBase.EventModel;
import com.appswallet.jamatfinder.FireBase.FirebaseReferences;
import com.appswallet.jamatfinder.FireBase.MasjidModel;
import com.appswallet.jamatfinder.Network.FetchAddressIntentService;
import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.Utils.Const;
import com.appswallet.jamatfinder.Utils.MyLocation;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;



public class Addjamat extends AppCompatActivity implements OnMapReadyCallback, LocationListener, BottomSheetTimePickerDialog.OnTimeSetListener,MasjidJamatFragment.TransferTime{


    private Spinner jamatName;
    private Location location;
    private GoogleMap map;
    private Button jamatButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference jamatRef = database.getReference(FirebaseReferences.eventRef);
    private DatabaseReference masjidRef = database.getReference(FirebaseReferences.masjidRef);
    private SupportMapFragment mapFragment;
    private TextView time_tv;
    private TextView location_tv;
    private java.util.Calendar now;
    private String time;
    private AddressResultReceiver mResultReciever;
    private String mAddressOutput;
    private LinearLayout timelayout;
    private RadioGroup rGroup;
    private RadioButton radio;
    private FragmentManager fragmentManager;
    private MasjidJamatFragment masjidFragment;
    private String fajarTime;
    private String zuharTime;
    private String asarTime;
    private String magribTime;
    private String ishaTime;
    private String masjidName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjamat);
        initView();
        startListener();
    }


    private void initView() {

        jamatButton = (Button) findViewById(R.id.jamat_btn);
        jamatName = (Spinner) findViewById(R.id.spinner);
        jamatName.setPrompt("Select Namaz");
        now = java.util.Calendar.getInstance();
        time_tv = (TextView) findViewById(R.id.time_id);
        location_tv = (TextView) findViewById(R.id.location_address);
        timelayout = (LinearLayout) findViewById(R.id.time_layout);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        rGroup = (RadioGroup) findViewById(R.id.add_radio);
        mapFragment.getMapAsync(this);
        location = MyLocation.getInstance().getLocation();
        mResultReciever = new AddressResultReceiver(null);
    }

    private void startListener() {
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.add_masjid) {
                    fragmentManager = getFragmentManager();
                    masjidFragment = new MasjidJamatFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.jamat_container, masjidFragment, "add");
                    fragmentTransaction.commit();
                    jamatName.setVisibility(View.GONE);
                    timelayout.setVisibility(View.GONE);

                }else
                {
                    if(fragmentManager!=null)
                    {
                        getFragmentManager().beginTransaction().remove(masjidFragment).commit();
                    }
                    jamatName.setVisibility(View.VISIBLE);
                    timelayout.setVisibility(View.VISIBLE);

                }



            }
        });

        jamatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rGroup.getCheckedRadioButtonId();
                if(selectedId == R.id.add_masjid)
                {
                    masjidFragment.passData();
                    createMasjid();
                }
                else
                {
                     createJamat();
                }


            }
        });

        timelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridTimePickerDialog grid = GridTimePickerDialog.newInstance(
                        Addjamat.this,
                        now.get(java.util.Calendar.HOUR_OF_DAY),
                        now.get(java.util.Calendar.MINUTE),
                        DateFormat.is24HourFormat(Addjamat.this));
                grid.show(getSupportFragmentManager(), Const.TAG);
            }
        });
    }

    private void createMasjid() {
        MasjidModel masjid = new MasjidModel();
        masjid.setFajarTime(fajarTime);
        masjid.setZuharTime(zuharTime);
        masjid.setAsarTime(asarTime);
        masjid.setMagribTime(magribTime);
        masjid.setIshaTime(ishaTime);
        masjid.setMasjidName(masjidName);
        masjid.setLatitude(location.getLatitude());
        masjid.setLongitude(location.getLongitude());
        masjid.setMasjidlocationName(location_tv.getText().toString());

        if (fajarTime == null || zuharTime == null || asarTime == null || magribTime == null || ishaTime == null || masjidName.toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            masjidRef.push().setValue(masjid);
            finish();
        }

    }

    private void createJamat() {
        EventModel jamat = new EventModel();
        jamat.setEventName(jamatName.getSelectedItem().toString());
        jamat.setEventlocationName(location_tv.getText().toString());
        jamat.setEventTime(time);
        jamat.setLatitude(location.getLatitude());
        jamat.setLongitude(location.getLongitude());

        if (location_tv.getText().toString().equals("") || time == null || location == null) {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG).show();
        } else {
            String key = jamatRef.push().getKey();
            jamatRef.child(key).setValue(jamat);


            //startCountDown(key);
            finish();
           // createSuccessDialog();
        }


    }

    private void createSuccessDialog() {

            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("Jamat ")
                    .content("Jamat is created Successfully " +
                            "Note : This Jamat will remove from map after 15 min")
                    .show();

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        map.setMyLocationEnabled(true);
        if(location != null) {
            addMarker();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14.0f));
            startIntentService();
        }
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                LatLng centre =  map.getCameraPosition().target;
                if(centre != null) {
                    location.setLatitude(centre.latitude);
                    location.setLongitude(centre.longitude);
                    startIntentService();
                }
            }
        });



    }

    private void addMarker() {
            LatLng marker = new LatLng(location.getLatitude(),location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(marker));

    }


    @Override
    public void onLocationChanged(Location location) {
        startIntentService();
        this.location = location;

    }


    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute){
        now.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(java.util.Calendar.MINUTE, minute);
        time = DateFormat.getTimeFormat(this).format(now.getTime());
        time_tv.setText(":  Time set: " + time);
    }



    private void startIntentService() {

        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Const.RECEIVER, mResultReciever);
        intent.putExtra(Const.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    @Override
    public void passTime(String fajar, String zuhar, String asar, String magrib, String isha,String masjidName) {
        fajarTime = fajar;
        zuharTime = zuhar;
        asarTime = asar;
        magribTime = magrib;
        ishaTime = isha;
        this.masjidName = masjidName;
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == Const.SUCCESS_RESULT) {
                mAddressOutput = resultData.getString(Const.RESULT_DATA_KEY);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayLocation();
                    }
                });

            }
            else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        location_tv.setText("Location not available");
                    }
                });

            }


        }
    }

    private void displayLocation() {
        location_tv.setText(mAddressOutput);

    }


    @Override
    protected void onPause() {
        super.onPause();

    }




   }

