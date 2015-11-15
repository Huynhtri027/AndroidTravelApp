package com.sutd.zhangzhexian.travelapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
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
    Button ClearAll;
    ListView List;
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
        ClearAll = (Button) root.findViewById(R.id.clearbtn);
        List = (ListView) root.findViewById(R.id.attractionList);
        adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
        Add.setOnClickListener(this);
        Generate.setOnClickListener(this);
        ClearAll.setOnClickListener(this);
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
                adb.setMessage("Are you sure?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.attractList.remove(positionToRemove);
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
                if (searchText.equals("")){ //prevent from if i dont type anything, displayed result is NUS
                    String locationName = AttractionLocator.correctedSearch(searchText);
                    MainActivity.attractList.add(locationName);      //add attraction
                    adapter.add(locationName);
                    Attraction.setText(""); //this sets textbox to null
                }
                break;

            case R.id.generateIt:       //generate button clicked
                if (MainActivity.attractList.isEmpty()||MainActivity.attractList.size()<2){
                    AlertDialog.Builder adb = new AlertDialog.Builder(root.getContext());
                    adb.setTitle("Error");
                    adb.setMessage("Minimum attractions enetered should be 2!");
                    adb.setNegativeButton("OK, got it", null);
                    adb.show();
                }
                else {
                    try {
                        DailyItinerary.setAttractionList(MainActivity.attractList.toArray(new String[MainActivity.attractList.size()]), Double.parseDouble(Budget.getText().toString()));
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    DailyItinerary nextFrag = new DailyItinerary();
                    this.getFragmentManager().beginTransaction()
                            .replace(this.getId(), nextFrag, null)
                            .addToBackStack(null)
                            .commit();
                }
                break;

            case R.id.clearbtn:
                MainActivity.attractList.clear();
                adapter.clear();
                adapter.notifyDataSetChanged();
               /* int size = MainActivity.attractList.size();
                for(int i=0;i<size;i++){
                    MainActivity.attractList.remove(i);
                    adapter.remove(adapter.getItem(i));
                    adapter.notifyDataSetChanged();
                }*/

        }
    }
}
