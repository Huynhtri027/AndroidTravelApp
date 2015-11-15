package com.sutd.zhangzhexian.travelapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sutd.zhangzhexian.travelapp.algorithm.Solver;
import com.sutd.zhangzhexian.travelapp.database.SolutionSet;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by Lakshita on 11/4/2015.
 */
public class DailyItinerary extends Fragment {

    View root;
    TextView Cost;
    TextView Time;
    static SolutionSet myRoute;
    ListView Daily;
    CustomListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.daily_itinerary, container, false);

        return root;
    }
      /*  Daily = (ListView)root.findViewById(R.id.dailyList);
        Cost = (TextView)root.findViewById(R.id.costText);
        Time = (TextView)root.findViewById(R.id.timeText);
        Image = (ImageView)root.findViewById(R.id.line);
        //Image.setImageResource(R.drawable.line);
        Cost.setText("Cost: "+ ((int) myRoute.cost));
        Time.setText("Duration: "+ ((int) myRoute.time)+"mins");

        ArrayList dailyIt = new ArrayList();

        //dailyIt = getListData();

        for (int i = 0; i < myRoute.route.length; i++){
            ListItems results = new ListItems(myRoute.route[i],myRoute.method[i]);
            System.out.println(myRoute.route[i]);
            dailyIt.add(results);
        }
        final ListView lv1 = (ListView) root.findViewById(R.id.dailyList);

        CustomListAdapter adapter = new CustomListAdapter(root.getContext(), dailyIt);

        lv1.setAdapter(adapter);

        return root;
    }*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Daily = (ListView)root.findViewById(R.id.dailyList);
        Cost = (TextView)root.findViewById(R.id.costText);
        Time = (TextView)root.findViewById(R.id.timeText);
        Cost.setText("Cost: S$"+ 0);
        Time.setText("Duration: " + 0 + "mins");

    }

    @Override
    public void onStart(){
        if (MainActivity.attractList.isEmpty()) {
            Cost.setText("Cost: S$"+ 0);
            Time.setText("Duration: " + 0 + "mins");
        }
        else {
            try {
                System.out.println(MainActivity.attractList);
                String[] solveList = MainActivity.attractList.toArray(new String[MainActivity.attractList.size()]);
                double budget = Double.parseDouble(AttractionList.Budget.getText().toString());
                Solver.getSolution(solveList, budget);
                //setAttractionList(MainActivity.attractList.toArray(new String[MainActivity.attractList.size()]), Double.parseDouble(AttractionList.Budget.getText().toString()));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            ArrayList dailyIt = new ArrayList();
            ListItems results;
            int size = SolutionSet.route.length;
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    results = new ListItems(SolutionSet.route[i], null);
                } else
                    results = new ListItems(SolutionSet.route[i], SolutionSet.method[i + 1]);


                dailyIt.add(results);
            }
            adapter = new CustomListAdapter(root.getContext(), dailyIt);
            Cost.setText("Cost: " + ((int) SolutionSet.cost));
            Time.setText("Duration: " + ((int) SolutionSet.time) + "mins");

            Daily.setAdapter(adapter);
        }
        super.onStart();
    }



    public static void setAttractionList(String[] solveList, double budget) throws CloneNotSupportedException {
        myRoute = Solver.getSolution(solveList, budget);

    }




}






