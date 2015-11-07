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
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
public class AttractionLocator extends Fragment implements OnMapReadyCallback {

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
        //buildGoogleApiClient();
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
        //mMap.setMyLocationEnabled(true);
        //Set default current location to MBS
        LatLng currentLocation = new LatLng(1.2826, 103.8584);
        mMap.clear();
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
        ArrayList<String> list = new ArrayList<>();
        list.add("Abdul Gaffoor Mosque");
        list.add("Al-Abrar Mosque");
        list.add("ArtScience Museum");
        list.add("Asian Civilisations Museum");
        list.add("Bright Hill Temple (Khong Meng San Phor Kark See Temple)");
        list.add("Buddha Tooth Relic Temple");
        list.add("Bukit Timah Nature Reserve");
        list.add("Cathedral of the Good Shepherd");
        list.add("Central Catchment Nature Reserve");
        list.add("Central Sikh Temple");
        list.add("Changi Prison Chapel and Museum");
        list.add("Chijmes");
        list.add("Chinatown Heritage Centre");
        list.add("Chinese and Japanese Gardens");
        list.add("Chinese Methodist Church");
        list.add("Church of St Gregory the Illuminator");
        list.add("Crane Dance");
        list.add("East Coast Park");
        list.add("Esplanade -Theatres on the Bay");
        list.add("Eu Yan Sang Chinese Medical Hall");
        list.add("Fort Canning Park");
        list.add("Gardens by the Bay");
        list.add("Geylang Serai Market");
        list.add("G-Max Reverse Bungy");
        list.add("Hajjah Fatimah Mosque");
        list.add("Haw Par Villa");
        list.add("HortPark");
        list.add("House of Tan Teng Niah");
        list.add("Images of Singapore");
        list.add("Istana");
        list.add("Jamae Mosque");
        list.add("Joo Chiat Street");
        list.add("Jurong Bird Park");
        list.add("Katong");
        list.add("Kranji War Memorial");
        list.add("Kusu Island");
        list.add("Kwan Im Thong Hood Cho Temple");
        list.add("Lau Pa Sat (Telok Ayer Market)");
        list.add("Leong San See Temple");
        list.add("Maghain Aboth Synagogue");
        list.add("Malay Heritage Centre");
        list.add("Malay Village");
        list.add("Mandai Orchid Gardens");
        list.add("Marina Barrage");
        list.add("Marina Bay Sands Casino");
        list.add("Marina Bay Sands SkyPark");
        list.add("Maxwell Road Hawker Centre");
        list.add("Merlion Park");
        list.add("Mount Faber Park");
        list.add("Nagore Durgha Shrine");
        list.add("National Museum of Singapore");
        list.add("National Orchid Garden");
        list.add("NEWater Visitor Centre");
        list.add("Old Parliament House");
        list.add("Peranakan Museum");
        list.add("Pulau Ubin");
        list.add("Raffles Hotel");
        list.add("Raffles Place");
        list.add("Red Dot Design Museum");
        list.add("Resort World Sentosa Casino");
        list.add("Sentosa Skyline Luge and Skyride");
        list.add("Singapore Art Museum");
        list.add("Singapore Bazaar and Flea Market");
        list.add("Singapore Botanic Gardens");
        list.add("Singapore Butterfly and Insect Kingdom");
        list.add("Singapore Discovery Centre");
        list.add("Singapore F1 Grand Prix");
        list.add("Singapore Flyer");
        list.add("Singapore Mint Coin Gallery");
        list.add("Singapore Navy Museum");
        list.add("Singapore Night Safari");
        list.add("Singapore Philatelic Museum");
        list.add("Singapore River");
        list.add("Science Centre Singapore");
        list.add("Singapore Zoo");
        list.add("Siong Lim Temple");
        list.add("Sisters' Islands");
        list.add("Songs of the Sea");
        list.add("Sri Krishnan Temple");
        list.add("Sri Mariamman Temple");
        list.add("Sri Srinivasa Perumal Temple");
        list.add("Sri Thandayuthapani Temple");
        list.add("Sri Veeramakaliamman Temple");
        list.add("St Andrew's Cathedral");
        list.add("Statues of Sir Stamford Raffles");
        list.add("St John's Island");
        list.add("Sultan Mosque");
        list.add("Sungei Buloh Nature Park");

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
}
