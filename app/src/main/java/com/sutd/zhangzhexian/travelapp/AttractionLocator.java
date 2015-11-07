package com.sutd.zhangzhexian.travelapp;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class AttractionLocator extends Fragment implements OnMapReadyCallback,
        ConnectionCallbacks, OnConnectionFailedListener {

    GoogleMap mMap;
    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.attraction_locator, container, false);
        // TODO: 5/11/15 Some Problem with Fragment
        //only for activity
        /*setContentView(R.layout.activity_maps);*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        mMap = mapFragment.getMap();
        Button button1 = (Button) root.findViewById(R.id.searchButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEditText = (EditText) getView().findViewById(R.id.searchBox);
                String searchText = searchEditText.getText().toString();
                String locationName = correctedSearch(searchText);
                Geocoder myGeocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> matchedList = null;
                try {
                    matchedList = myGeocoder.getFromLocationName(locationName, 1);
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
                double lat = matchedList.get(0).getLatitude();
                double lon = matchedList.get(0).getLongitude();
                LatLng locationDetails = new LatLng(lat,lon);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(locationDetails));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationDetails,(float) 13.5));
            }
        });

        return root;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LatLng currentLocation = new LatLng(currentLat, currentLong);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(currentLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,(float) 13.5));
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

    // Robust search, returns corrected name location
    public static String correctedSearch(String search){
        ArrayList<String> list = new ArrayList<>();
        list.add("Sentosa");
        list.add("Marina Bay Sands");
        list.add("Changi Airport");
        list.add("Orchard Road");
        list.add("Pulau Ubin");

        ArrayList<Integer> myList = new ArrayList<>();
        Hashtable balance = new Hashtable();
        for (String place:list){
            int value = minDistance(search, place);
            balance.put(value,place);
            myList.add(value);
        }
        Collections.sort(myList);
        return (String) balance.get(myList.get(0));
    }

    // Here is where I get the current location of the user (aka Last Known Location of device)

    protected static final String TAG = "AttractionLocator";
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected double currentLat;
    protected double currentLong;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        currentLat = mLastLocation.getLatitude();
        currentLong = mLastLocation.getLongitude();
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
