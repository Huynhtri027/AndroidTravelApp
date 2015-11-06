package com.sutd.zhangzhexian.travelapp;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class AttractionLocator extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    protected static final String TAG = "AttractionLocator";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    GoogleMap mMap;
    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.attraction_locator, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        return root;
    }
    public String correctedSearch(String text){
        ArrayList<String> list = new ArrayList<>();
        list.add("Sentosa");
        list.add("Marina Bay Sands");
        list.add("Changi Airport");
        list.add("Orchard Road");
        list.add("Pulau Ubin");

        ArrayList<Integer> myList = new ArrayList<>();
        Hashtable balance = new Hashtable();
        for (String place:list){
            int value = minDistance(text, place);
            balance.put(value,place);
            myList.add(value);
        }
        Collections.sort(myList);
        return (String) balance.get(myList.get(0));
    }
    public void onMapReady(GoogleMap googleMap) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        double lat = mLastLocation.getLatitude();
        double lon = mLastLocation.getLongitude();
        LatLng currentLocation = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(currentLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

    }
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // len1+1, len2+1, because finally return dp[len1][len2]
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        //iterate though, and check last char
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                //if last two chars equal
                if (c1 == c2) {
                    //update dp value for +1 length
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }
    /*
    public void showSearchedLocation(GoogleMap googleMap){
        mMap = googleMap;
        EditText lctString = (EditText) getView().findViewById(R.id.search_box);
        String locationString = lctString.getText().toString();
        Geocoder myGeocoder = new Geocoder(getContext());
        List<Address> matchedList = null;
        try {
            matchedList = myGeocoder.getFromLocationName(locationString, 1);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        double lat = Double.parseDouble(String.valueOf(matchedList.get(0).getLatitude()));
        double lon = Double.parseDouble(String.valueOf(matchedList.get(0).getLongitude()));
        LatLng newLocation = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(newLocation).title(correctedSearch(locationString)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
    }
    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     * may have to change 'this' to getContext()
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        /*mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                    mLastLocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                    mLastLocation.getLongitude()));
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
}
