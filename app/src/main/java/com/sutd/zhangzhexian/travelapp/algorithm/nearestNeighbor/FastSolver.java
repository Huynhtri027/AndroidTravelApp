package com.sutd.zhangzhexian.travelapp.algorithm.nearestNeighbor;

import com.sutd.zhangzhexian.travelapp.algorithm.model.SolutionSet;
import com.sutd.zhangzhexian.travelapp.database.Data;

/**
 * Created by zhanghao on 7/11/15.
 */
public class FastSolver {

    public static SolutionSet solve(String[] listOfAttractions, double budget){
        double remainingDistance=0;
        double remainingBudget=budget;
        Data data=new Data(listOfAttractions);
        Route newRoute=new Route(listOfAttractions);
        SolutionSet.route=newRoute.getFollowedRouteString();
        SolutionSet.method=new String[listOfAttractions.length];
        for (String l :SolutionSet.route) {
            System.out.println(l);
        }

        //get total distance
        for (int i = 0; i < SolutionSet.route.length-1; i++) {
            remainingDistance+= data.getDistance(data.getAttractionId(SolutionSet.route[i]),
                                                data.getAttractionId(SolutionSet.route[i+1]));
        }

        for (int i = 1; i < SolutionSet.route.length; i++) {
            remainingDistance-= data.getDistance(data.getAttractionId(SolutionSet.route[i-1]),
                    data.getAttractionId(SolutionSet.route[i]));

            if (remainingBudget/remainingDistance>1 & data.getTaxiCost(data.getAttractionId(SolutionSet.route[i - 1]),
                    data.getAttractionId(SolutionSet.route[i]))<remainingBudget){
                //take taxi
                SolutionSet.method[i]="taxi";
                SolutionSet.time+=data.getTaxiTime(data.getAttractionId(SolutionSet.route[i - 1]),
                        data.getAttractionId(SolutionSet.route[i]));
                remainingBudget-=data.getTaxiCost(data.getAttractionId(SolutionSet.route[i - 1]),
                        data.getAttractionId(SolutionSet.route[i]));
            }
            else if(remainingBudget>2.04 &data.getPublicTime(data.getAttractionId(SolutionSet.route[i - 1]),
                    data.getAttractionId(SolutionSet.route[i]))<remainingBudget){
                //take public transport
                SolutionSet.method[i]="public transport";
                SolutionSet.time+=data.getPublicTime(data.getAttractionId(SolutionSet.route[i - 1]),
                        data.getAttractionId(SolutionSet.route[i]));
                remainingBudget-=data.getPublicCost(data.getAttractionId(SolutionSet.route[i - 1]),
                        data.getAttractionId(SolutionSet.route[i]));
            }
            else{
                //walk
                SolutionSet.method[i]="walk";
                SolutionSet.time+=data.getWalkTime(data.getAttractionId(SolutionSet.route[i - 1]),
                        data.getAttractionId(SolutionSet.route[i]));
            }
        }
        SolutionSet.cost=budget-remainingBudget;
        return new SolutionSet();
    }


    public static void main(String[] args) {
        String[] list={"nothing","Sentosa","Singapore Flyer","Vivo City"};
        solve(list,25);
        System.out.println("Start from "+SolutionSet.route[0]);
        for (int i = 1; i < SolutionSet.route.length; i++) {
            System.out.println(SolutionSet.method[i]+" to "+SolutionSet.route[i]);
        }
        System.out.println("cost:"+SolutionSet.cost+ "Time:"+SolutionSet.time);
    }
}
