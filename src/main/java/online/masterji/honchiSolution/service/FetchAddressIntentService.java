package online.masterji.honchiSolution.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;


import java.util.List;
import java.util.Locale;

import online.masterji.honchiSolution.constant.Constants;

public class FetchAddressIntentService extends IntentService {

    private static final String TAG = "FetchAddressIntentServi";

    protected ResultReceiver receiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchAddressIntentService(String name) {
        super(name);
    }

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Log.e(TAG, "completeAddress: " + message);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        if (receiver != null)
            receiver.send(resultCode, bundle);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String completeAddress = "";
        try {
            if (intent == null) {
                return;
            }
            receiver = intent.getParcelableExtra(Constants.RECEIVER);
            LatLng latLng = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
            Log.e(TAG, "latitude: " + latLng.latitude + " longitude: " + latLng.longitude);


            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    if (i == 0) {
                        completeAddress = address.getAddressLine(i);
                    } else {
                        completeAddress += address.getAddressLine(i);
                    }
                }
            }
            deliverResultToReceiver(Constants.SUCCESS_RESULT, completeAddress);

        } catch (Exception e) {
            Log.e(TAG, "error", e);
            deliverResultToReceiver(Constants.FAILURE_RESULT, completeAddress);
        }


    }
}

