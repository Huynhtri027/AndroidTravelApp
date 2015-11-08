package com.sutd.zhangzhexian.travelapp.algorithm;

/**
 * Created by zhanghao on 7/11/15.
 */
import com.sutd.zhangzhexian.travelapp.algorithm.model.SolutionSet;
import com.sutd.zhangzhexian.travelapp.algorithm.nearestNeighbor.FastSolver;


/**
 * Created by Zhang Hao on 7/11/15.
 */
public class Solver {


    /**Last edited by Zhang Hao on 7/11/15.
     *
     * To get a solution,use the following code:
     * Solver.getSolution(attractionList,budget);
     *
     * The solution can be accessed using:
     * SolutionSet.route    the planned route in array form.
     *                      eg.SolutionSet.route={"place 1","place 2,..., "place 1}
     *                      note that it will always return to the start place
     * SolutionSet.method;  the type of transport in array form.
     *                      eg.SolutionSet.method={"nothing","taxi","public transport",...}
     *                      note that the first element is always meaningless
     * SolutionSet.time;    total time of daily travel
     * SolutionSet.cost;    total cost of daily travel
     *
     *
     * @param attractionList a String[] containing all the attractions, note
     *                       that first element is not used.
     *                       eg. attractionList={"nothing","place 1","place 2"...}
     * @param budget variable type double, the budget input.
     * @return update the new SolutionSet static class
     */
    public static SolutionSet getSolution(String[] attractionList,double budget){
        if (attractionList.length<10){
            // TODO: 7/11/15 brute force haven't implemented yet, use fast approximate instead temperately
            FastSolver.solve(attractionList,budget);
        }
        else{
            FastSolver.solve(attractionList,budget);
        }
        return new SolutionSet();
    }
    //Test code
    /*public static void main(String[] args) {
        String[] list={"nothing","Sentosa","Singapore Flyer","Vivo City"};
        getSolution(list, 25);
        System.out.println("Start from "+SolutionSet.route[0]);
        for (int i = 1; i < SolutionSet.route.length; i++) {
            System.out.println(SolutionSet.method[i]+" to "+SolutionSet.route[i]);
        }
        System.out.println("cost:"+SolutionSet.cost+ "Time:"+SolutionSet.time);
    }*/
}