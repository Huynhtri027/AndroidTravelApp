package com.sutd.zhangzhexian.travelapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sutd.zhangzhexian.travelapp.database.Data;
import com.sutd.zhangzhexian.travelapp.database.SolutionSet;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class AttractionLocator extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    int n = 0; //if n = 0, map type is MAP_TYPE_NORMAL, else MAP_TYPE_SATELLITE
    PolylineOptions routeLine = new PolylineOptions().geodesic(true);
    Geocoder myGeocoder;
    MarkerOptions marker;
    String locationName;
    String searchText;
    EditText searchEditText;
    Button search_button;
    Button add_button;
    Button change_view_button;
    Button itinerary;
    GoogleMap mMap;
    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.attraction_locator, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        myGeocoder = new Geocoder(getActivity(), Locale.getDefault());
        mMap = mapFragment.getMap();

        add_button = (Button) root.findViewById(R.id.add_button);
        add_button.setOnClickListener(this);

        search_button = (Button) root.findViewById(R.id.search_button);
        search_button.setOnClickListener(this);

        change_view_button = (Button) root.findViewById(R.id.change_view_button);
        change_view_button.setOnClickListener(this);

        itinerary = (Button) root.findViewById(R.id.itinerary);
        itinerary.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                searchEditText = (EditText) getView().findViewById(R.id.search_box);
                searchText = searchEditText.getText().toString();
                locationName = correctedSearch(searchText);
                List<Address> matchedList = null;
                try {
                    matchedList = myGeocoder.getFromLocationName(locationName, 1);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                double lat = matchedList.get(0).getLatitude();
                double lon = matchedList.get(0).getLongitude();
                LatLng locationDetails = new LatLng(lat, lon);
                marker = new MarkerOptions().position(locationDetails);
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationDetails, (float) 14));
                break;

            case R.id.add_button:
                if (MainActivity.attractList.isEmpty()){
                    MainActivity.attractList.add("nothing");
                    MainActivity.attractList.add("Marina Bay Sands");
                }
                searchEditText = (EditText) getView().findViewById(R.id.search_box);
                searchText = searchEditText.getText().toString();
                locationName = correctedSearch(searchText);
                MainActivity.attractList.add(locationName);
                // re-solve using a new attractList

                showPolyline();
                break;

            case R.id.change_view_button:
                if (n==0)
                    mMap.setMapType(MAP_TYPE_SATELLITE);
                else
                    mMap.setMapType(MAP_TYPE_NORMAL);
                n = 1-n;
                break;

            case R.id.itinerary:

                DailyItinerary nextFrag= new DailyItinerary();
                this.getFragmentManager().beginTransaction()
                        .replace(this.getId(), nextFrag ,null)
                        .addToBackStack(null)
                        .commit();
        }
    }

    public void showPolyline(){
        for (int i=0; i< DailyItinerary.myRoute.route.length; i++){

            List<Address> place = null;
            try {
                String correctedNameInSingapore = SolutionSet.route[i] + " Singapore";
                place = myGeocoder.getFromLocationName(correctedNameInSingapore, 1);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            double latPlace = place.get(0).getLatitude();
            double lonPlace = place.get(0).getLongitude();
            LatLng placeDetails = new LatLng(latPlace, lonPlace);
            MarkerOptions anotherMarker = new MarkerOptions().position(placeDetails);
            mMap.addMarker(anotherMarker);
            routeLine.add(placeDetails);
        }
        if (MainActivity.attractList.size()>1 && MainActivity.attractList!=null)
            mMap.addPolyline(routeLine);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(0,0,100,100);
        //Set default current location to MBS
        LatLng currentLocation = new LatLng(1.2826, 103.8584);
        if (MainActivity.attractList != null)
            showPolyline();
        mMap.addMarker(new MarkerOptions().position(currentLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,(float) 13.5));
    }

    public static int minDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    // Robust search, returns corrected name location
    public static String correctedSearch(String search){

        ArrayList<Integer> myList = new ArrayList<>();
        Hashtable balance = new Hashtable();
        for (int i=1; i < Data.attractionNames.length; i++){
            int value = minDistance(search, Data.attractionNames[i]);
            balance.put(value,Data.attractionNames[i]);
            myList.add(value);
        }
        Collections.sort(myList);
        return (String) balance.get(myList.get(0));
    }
}
