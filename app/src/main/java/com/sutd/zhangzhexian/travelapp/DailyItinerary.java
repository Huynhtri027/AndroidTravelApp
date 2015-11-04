package com.sutd.zhangzhexian.travelapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class DailyItinerary extends Fragment {

    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.daily_itinerary, container, false);
        return root;
    }



    //return the attraction id by attraction name
    public int getAttractionid(String attraction, String[] attractions){
        for (int i=1;i<=attraction.length();i++){
            if (attraction==attractions[i])
                return i;
        }
        return 0;
    }

    public String getAttractionName(int attraction, String[] attractions){
        return attractions[attraction];
    }

    public double getPublicCost(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    public double getTaxiCost(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    public double getPublicTime(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    public double getTaxiTime(int a, int b){
        // TODO: 4/11/15
        return 0;
    }
    public double getWalkTime(int a, int b){
        // TODO: 4/11/15
        return 0;
    }

    //1 for walk, 2 for public, 3 for taxi
    public int[][] bruteForceRoute(int currentAttraction, int nextAttraction, double currentBudget,
                                   double spent, int attractionCount, int attractionNumber,
                                   int[] remainingAttractions){
        int[][] returnList=new int[attractionNumber+1][2];
        for(int i=1; i<=remainingAttractions.length;i++){
            int newNextAttraction,newAttractionCount;
            newNextAttraction=remainingAttractions[i];
            newAttractionCount=attractionCount+1;
            int[] newRemainingAttractions=new int[remainingAttractions.length];
            double newCurrentBudget, newSpent;
            newNextAttraction=remainingAttractions[i];

            //walk
            newCurrentBudget=currentBudget;
            newSpent=spent;
            bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newSpent,newAttractionCount,attractionNumber,newRemainingAttractions);

            //public transport
            newCurrentBudget=currentBudget-getPublicCost(currentAttraction,nextAttraction);
            newSpent=spent+getPublicCost(currentAttraction,nextAttraction);
            bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newSpent,newAttractionCount,attractionNumber,newRemainingAttractions);

            //taxi
            newCurrentBudget=currentBudget-getTaxiCost(currentAttraction,nextAttraction);
            newSpent=spent+getTaxiCost(currentAttraction,nextAttraction);
            bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newSpent,newAttractionCount,attractionNumber,newRemainingAttractions);

        }






//        if (currentBudget<0)
//            returnList[0]=-1;




        return returnList;
    }




}
