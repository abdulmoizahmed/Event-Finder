package com.appswallet.jamatfinder.Network;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;

import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.Utils.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by android on 11/16/16.
 */
public class FetchAddressIntentService extends IntentService {
    protected ResultReceiver resultReceiver;
    String errorMessage = "";


    public FetchAddressIntentService()
    {
        super("GeocodeAddressIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;
        Location location = intent.getParcelableExtra(Const.LOCATION_DATA_EXTRA);
        resultReceiver = intent.getParcelableExtra(Const.RECEIVER);
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1
            );
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, e);
        }


        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {

                errorMessage = getString(R.string.no_address_found);

                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(Const.FAILURE_RESULT, errorMessage);

        } else {
            for (Address address : addresses) {

                String outputAddress = "";
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    outputAddress += " --- " + address.getAddressLine(i);
                }
            }

            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }

            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(Const.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }


    }

    private void deliverResultToReceiver(int resultcode, String message) {

        Bundle bundle = new Bundle();
        bundle.putString(Const.RESULT_DATA_KEY,message);
        resultReceiver.send(resultcode,bundle);
    }
}
