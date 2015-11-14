package com.sutd.zhangzhexian.travelapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
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
    ImageView Image;
    static SolutionSet myRoute;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.daily_itinerary, container, false);



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
    }





    public static void setAttractionList(String[] solveList, double budget) throws CloneNotSupportedException {
        myRoute = Solver.getSolution(solveList, budget);

    }




}






