package com.sutd.zhangzhexian.travelapp;

import android.app.Application;
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



   /* //return the attraction id by attraction name
    public int getAttractionid(String attraction, String[] attractions){
        for (int i=1;i<=attraction.length();i++){
            if (attraction==attractions[i])
                return i;
        }
        return 0;
    }

    public String getAttractionName(int attraction, String[] attractions){
        return attractions[attraction];
    }*/

    public int getMaxSize(int a){
        int ans=1;
        for (int i = 1; i < a; i++) {
            ans=ans*i*a;
        }
        return ans;
    }

    public int findPrevious(Route thisRoute){
        for (int i = 0; i < thisRoute.route.length; i++) {
            if (!thisRoute.route[i].isPlanned()){
                return i;
            }
        }
        return 0;
    }

    public boolean allVisited(Attraction[] attractions){
        boolean mark=true;
        for (int i = 0; i < attractions.length; i++) {
            if(!attractions[i].visited)
                mark=false;
        }
        return mark;
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

    /*//1 for walk, 2 for public, 3 for taxi
    public double[][][] bruteForceRoute(int currentAttraction, int nextAttraction, double currentBudget,
                                   int attractionCount,
                                   int[] remainingAttractions, double[][] route, double[][][] solutionSet){
        double[][] newRoute=new double[route.length][route[0].length];
        for (int i = 0; i < route.length; i++) {
            for (int j = 0; j < route[0].length ; j++) {
                newRoute[i][j]=route[i][j];
            }
        }
        for(int i=1; i<=remainingAttractions.length;i++){

            //initiation
            int newNextAttraction,newAttractionCount;
            newAttractionCount=attractionCount+1;
            int[] newRemainingAttractions=new int[remainingAttractions.length];
            double newCurrentBudget;
            newNextAttraction=remainingAttractions[i];

            //terminate condition
            if (currentBudget<0)
                newRoute[2][0]=-1;//unsatisfied
            else if (remainingAttractions.length==0){
                newRoute[2][0]=1;//finished
                // TODO: 5/11/15 add to solution set
                for (int i = 1; i <= solutionSet.length ; i++) {
                    if (solutionSet[i][0][2]!=0)
                        for (int p=1;p<=solutionSet[0].length)
                            for(int q=1;p<=solutionSet[0][0].length)
                                solutionSet[i][p][q]=route[p][q];
                }
            }else {
                //walk
                newCurrentBudget=currentBudget;
                newRoute[0][0]=newCurrentBudget;
                newRoute[0][1]+=getWalkTime(currentAttraction,nextAttraction);
                bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newAttractionCount,newRemainingAttractions,newRoute,solutionSet);

                //public transport
                newCurrentBudget=currentBudget-getPublicCost(currentAttraction,nextAttraction);
                newRoute[0][0]=newCurrentBudget;
                newRoute[0][1]+=getPublicTime(currentAttraction,nextAttraction);
                bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newAttractionCount,newRemainingAttractions,newRoute,solutionSet);

                //taxi
                newCurrentBudget=currentBudget-getTaxiCost(currentAttraction,nextAttraction);
                newRoute[0][0]=newCurrentBudget;
                newRoute[0][1]+=getTaxiTime(currentAttraction, nextAttraction);
                bruteForceRoute(nextAttraction,newNextAttraction,newCurrentBudget,newAttractionCount,newRemainingAttractions,newRoute,solutionSet);
            }
        }
        return solutionSet;
    }


    public int[][][] runBruteForceRoute(int attractions)


*/
    public int [][] fastApproximate(){
        // TODO: 4/11/15 implement this 
        return new int[1][1];
    }

    public Route[] bruteForce(int[] attractions){
        Attraction[] attractionList=new Attraction[attractions.length];
        for (int i = 0; i <attractions.length; i++)
            attractionList[i].visited=false;
        Route[] ans=new Route[getMaxSize(attractionList.length-1)];
        Route thisRoute=new Route(attractionList.length-1);
        return bruteForceSolve(attractionList,ans,thisRoute);
    }

    public Route[] bruteForceSolve(Attraction[] attractionList, Route[] ans, Route thisRoute){

        //add ans to answer set if finished
        if (allVisited(attractionList)){
            for (int i = 0; i < ans.length; i++) {
                if(!ans[i].done){
                    ans[i].done=true;
                    ans[i].cost=thisRoute.cost;
                    ans[i].time=thisRoute.time;
                    for (int j = 0; j < thisRoute.route.length; j++) {
                        ans[i].route[j].setTransport(thisRoute.route[j].getTransport());
                        ans[i].route[j].setAttractionId(thisRoute.route[j].getAttractionId());
                        ans[i].route[j].setPlanned(thisRoute.route[j].isPlanned());
                    }
                }
            }
            return ans;
        }

        //Copying objects
        Route newThisRoute=new Route(thisRoute.route.length);
        newThisRoute.attractionNumber=thisRoute.attractionNumber;
        newThisRoute.time=thisRoute.time;
        newThisRoute.cost=thisRoute.cost;
        newThisRoute.done=thisRoute.done;
        for (int i = 0; i < thisRoute.route.length; i++) {
            newThisRoute.route[i].setTransport(thisRoute.route[i].getTransport());
            newThisRoute.route[i].setAttractionId(thisRoute.route[i].getAttractionId());
            newThisRoute.route[i].setPlanned(thisRoute.route[i].isPlanned());
        }

        Attraction[] newAttractionList=new Attraction[attractionList.length];
        for (int i = 0; i < attractionList.length; i++) {
            newAttractionList[i].visited=attractionList[i].visited;
        }

        //find next attraction
        for (int i = 0; i < attractionList.length ; i++) {
            if (!attractionList[i].visited){
                //walk
                attractionList[i].visited=true;
                newThisRoute.cost+=0;
                newThisRoute.time+=getWalkTime(findPrevious(newThisRoute),i);
                newThisRoute.route[i].setAttractionId(i);
                newThisRoute.route[i].setTransport(1);
                newThisRoute.route[i].setPlanned(true);
                bruteForceSolve(newAttractionList, ans,newThisRoute);

                //public
                attractionList[i].visited=true;
                newThisRoute.cost+=getPublicCost(findPrevious(newThisRoute), i);
                newThisRoute.time+=getPublicTime(findPrevious(newThisRoute),i);
                newThisRoute.route[i].setAttractionId(i);
                newThisRoute.route[i].setTransport(2);
                newThisRoute.route[i].setPlanned(true);
                bruteForceSolve(newAttractionList,ans,newThisRoute);

                //taxi
                attractionList[i].visited=true;
                newThisRoute.cost+=getTaxiCost(findPrevious(newThisRoute),i);
                newThisRoute.time+=getTaxiTime(findPrevious(newThisRoute), i);
                newThisRoute.route[i].setAttractionId(i);
                newThisRoute.route[i].setTransport(3);
                newThisRoute.route[i].setPlanned(true);
                bruteForceSolve(newAttractionList,ans,newThisRoute);
            }
        }
        return ans;
    }


}

class Location{
    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    private int attractionId;
    private int transport;
    private boolean planned;

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean planned) {
        this.planned = planned;
    }



    public Location(int attractionId, int transport, boolean planned) {
        this.attractionId = attractionId;
        this.transport = transport;
        this.planned=planned;
    }
}

class Route{
    int attractionNumber;
    double cost;
    double time;
    boolean done;
    Location[] route;

    public Route(int attractionNumber) {
        this.attractionNumber = attractionNumber;
        route=new Location[attractionNumber];
    }
}

class Attraction{
    boolean visited;

    public Attraction(boolean visited) {
        this.visited = visited;
    }
}
