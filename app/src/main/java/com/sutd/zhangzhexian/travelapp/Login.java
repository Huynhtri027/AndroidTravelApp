package com.sutd.zhangzhexian.travelapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sutd.zhangzhexian.travelapp.algorithm.model.Attraction;


/**
 * Created by Lakshita on 11/4/2015.
 */
public class Login extends Fragment {

    View root;
    EditText Name;
    EditText Country;
    EditText Age;
    Button go;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.login_page, container, false);
        Name = (EditText)root.findViewById(R.id.userName);
        Country = (EditText)root.findViewById(R.id.userCountry);
        Age = (EditText)root.findViewById(R.id.userAge);
        go = (Button)root.findViewById(R.id.travel);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttractionList nextFrag = new AttractionList();
                getFragmentManager().beginTransaction()
                        .replace(getId(), nextFrag, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return root;
    }




}
