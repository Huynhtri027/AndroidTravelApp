package com.sutd.zhangzhexian.travelapp.algorithm.slow;

import com.sutd.zhangzhexian.travelapp.algorithm.model.RouteListSet;
import com.sutd.zhangzhexian.travelapp.database.Permutations;
import com.sutd.zhangzhexian.travelapp.database.SolutionSet;

/**
 * Created by zhanghao on 12/11/15.
 */
public class SlowSolver {
    public static SolutionSet solve(String[] namesOfAttraction,double budget) throws CloneNotSupportedException {
        GeneratePermutation.getPermutation(namesOfAttraction);
        GenerateMethods.getMethods(namesOfAttraction.length);


        for (int i = 0; i < Permutations.all.size(); i++) {
            String[] route=new String[namesOfAttraction.length];
            route[0]=namesOfAttraction[1];
            route[namesOfAttraction.length-1]=namesOfAttraction[1];
            for (int j = 0; j < Permutations.all.get(i).length; j++) {
                route[j+1]=Permutations.all.get(i)[j];
            }


            GetAllRoute.add(route);
        }


        int bestId=Integer.MIN_VALUE;
        double bestTime=Double.MAX_VALUE;
        for (int i = 1; i < RouteListSet.allRoutes.size(); i++) {
            if (RouteListSet.allRoutes.get(i).cost<=budget & RouteListSet.allRoutes.get(i).time<bestTime){
                bestId=i;
                bestTime=RouteListSet.allRoutes.get(i).time;
            }
        }

        String[] ansRoute=new String[namesOfAttraction.length];
        String[] ansMethod=new String[namesOfAttraction.length];

        for (int i = 0; i < namesOfAttraction.length; i++) {
            ansRoute[i]=RouteListSet.allRoutes.get(bestId).singleRoute[i].getName();
            ansMethod[i]=RouteListSet.allRoutes.get(bestId).singleRoute[i].getMethodToThis();
        }

        SolutionSet.time=bestTime/60;
        SolutionSet.cost= RouteListSet.allRoutes.get(bestId).cost;
        SolutionSet.route=ansRoute.clone();
        SolutionSet.method=ansMethod.clone();




        return new SolutionSet();
    }
}
