package com.sutd.zhangzhexian.travelapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sutd.zhangzhexian.travelapp.database.DataBase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class AttractionList extends Fragment implements View.OnClickListener {

    View root;

    static EditText Budget;
    EditText Attraction;
    Button Add;
    Button Generate;
    ListView List;
    static List<String> attractList;
    ArrayAdapter<String> adapter;
    FrameLayout frame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.attractions_list, container, false);

        frame = new FrameLayout(root.getContext());

        Budget = (EditText) root.findViewById(R.id.budgetamt);
        Attraction = (EditText) root.findViewById(R.id.attraction);
        Add = (Button) root.findViewById(R.id.add);
        Generate = (Button) root.findViewById(R.id.generateIt);
        List = (ListView) root.findViewById(R.id.attractionList);
        attractList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
        Add.setOnClickListener(this);
        Generate.setOnClickListener(this);

        return root;
    }



    @Override
    public void onClick(View v) {
        List = (ListView) root.findViewById(R.id.attractionList);
        List.setAdapter(adapter);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(root.getContext());
                adb.setTitle("Delete");
                adb.setMessage("Are you sure you want to delete?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        attractList.remove(positionToRemove);
                        adapter.remove(adapter.getItem(positionToRemove));
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });


        switch (v.getId()) {
            case R.id.add:      //add button clicked
                String searchText = Attraction.getText().toString();
                String locationName = correctedSearch(searchText);
                attractList.add(locationName);      //add attraction
                adapter.add(locationName);
                Attraction.setText(""); //this sets textbox to null
                break;

            case R.id.generateIt:       //generate button clicked
                DailyItinerary.setAttractionList(attractList.toArray(new String[attractList.size()]), Double.parseDouble(Budget.getText().toString()));

                DailyItinerary nextFrag= new DailyItinerary();
                this.getFragmentManager().beginTransaction()
                        .replace(this.getId(), nextFrag ,null)
                        .addToBackStack(null)
                        .commit();
        }
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

    public static String correctedSearch(String search){
        ArrayList<Integer> myList = new ArrayList<>();
        Hashtable balance = new Hashtable();
        for (int i=1; i < DataBase.attractionNames.length; i++){
            int value = minDistance(search, DataBase.attractionNames[i]);
            balance.put(value,DataBase.attractionNames[i]);
            myList.add(value);
        }
        Collections.sort(myList);
        return (String) balance.get(myList.get(0));
    }

}
