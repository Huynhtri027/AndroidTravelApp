package com.sutd.zhangzhexian.travelapp.algorithm.nearestNeighbor;

/**
 * Created by zhanghao on 7/11/15.
 */

import com.sutd.zhangzhexian.travelapp.database.Data;

import java.util.ArrayList;


public class Route {

    public static ArrayList<String> followedRouteString;
    public static ArrayList<Integer> followedRoute;
    public static int nodes = 0;
    public static int routeCost = 0;


    String[] places;

    public Route(String[] places) {
        this.places = places;
    }

    /*public static void main(String[] args) {
        System.out.println(getFollowedRouteString(places));
    }*/

    public String[] getFollowedRouteString() {
        Data data=new Data(places);

        followedRoute = new ArrayList<Integer>();
        followedRouteString = new ArrayList<String>();
        followedRoute.add(1);
        nodes++;

        long startTime = System.currentTimeMillis();
        search(1,places);
        long endTime = System.currentTimeMillis();

        followedRouteString = new ArrayList<String>();
        for (int j=0; j<followedRoute.size(); j++){
            String ss = new String(data.getAttractionNames(followedRoute.get(j)));
            followedRouteString.add(ss);
        }

        /*String result = new String();
        result = "-------------------------------------\n";
        result += "NEAREST NEIGHBOUR:\n";
        result += "-------------------------------------\n";
        result += "BEST SOLUTION: \t"+followedRouteString + "\nDISTANCE: \t\t"+routeCost+"\n";
        result += "VISITED NODES: \t"+nodes+"\n";
        result += "ELAPSED TIME: \t"+(endTime-startTime)+" ms\n";
        result += "-------------------------------------\n";

        System.out.println(result);*/

        //return followedRouteString;

        String[] ans=new String[followedRouteString.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i]=followedRouteString.get(i);
        }
        return ans;
    }

    /**
     * @param from node where we start the search.
     */
    public static void search (int from, String[] places) {


        Data data=new Data(places);

        int currentTown = from;

        while (nodes != data.getNumberOfAttractions()) {
            // choose the closest town
            double lowestDistance = Integer.MAX_VALUE;
            int chosen = 0;
            for (int i=1; i < data.getNumberOfAttractions()+1; i++) {
                if (!followedRoute.contains(i)) {
                    double tempDistance = data.getDistance(currentTown, i);
                    if (tempDistance < lowestDistance) {
                        lowestDistance = tempDistance;
                        chosen = i;
                    }
                }
            }
            routeCost += data.getDistance(currentTown, chosen);
            followedRoute.add(chosen);
            currentTown = chosen;
            nodes++;
        }
        // add the last town
        routeCost += data.getDistance(currentTown, 1);
        followedRoute.add(1);
        nodes++;
    }
}