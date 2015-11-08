package com.sutd.zhangzhexian.travelapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sutd.zhangzhexian.travelapp.algorithm.Solver;
import com.sutd.zhangzhexian.travelapp.algorithm.model.SolutionSet;

import java.util.ArrayList;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class DailyItinerary extends Fragment {

    View root;
    static SolutionSet myRoute;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.daily_itinerary, container, false);
        return root;
    }

    public static void setAttractionList(String[] solveList, double budget){
        myRoute = Solver.getSolution(solveList, budget);
    }




}






