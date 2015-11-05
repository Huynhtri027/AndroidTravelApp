package com.sutd.zhangzhexian.travelapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class MapView extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;

    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.mapview, container, false);
        // TODO: 5/11/15 Some Problem with Fragment
        //only for activity
        /*setContentView(R.layout.activity_maps);*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(34, 117);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Beijing"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LatLng beijing = new LatLng(67,88);
        mMap.addMarker(new MarkerOptions().position(beijing).title("Somewhere"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(beijing));

        mMap.addPolyline(new PolylineOptions());
    }
}
