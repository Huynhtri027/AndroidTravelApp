package com.sutd.zhangzhexian.travelapp.database;

/**
 * Created by zhanghao on 7/11/15.
 */
public class DataBase {

    private static String[] attractionNames={"nothing","Marina Bay Sands","Singapore Flyer","Vivo City","Sentosa","Buddha Tooth Relic Temple","Zoo"};

    //distance in km
    private static double[][] distance=
            {{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0},
            {1.0, 0.0, 14.0, 69.0, 76.0, 28.0, 269.0},
            {2.0, 14.0, 0.0, 81.0, 88.0, 39.0, 264.0},
            {3.0, 69.0, 81.0, 0.0, 12.0, 47.0, 270.0},
            {4.0, 76.0, 88.0, 12.0, 0.0, 55.0, 285.0},
            {5.0, 28.0, 39.0, 47.0, 55.0, 0.0, 264.0},
            {6.0, 269.0, 264.0, 270.0, 285.0, 264.0, 0.0}};


    public static double getDistance(int a,int b){
        return distance[a][b];
    }

    public static double getWalkTime(int a,int b){
        return distance[a][b]/3;
    }

    public static double getPublicTime(int a, int b){
        return distance[a][b]/15;
    }

    public static double getTaxiTime(int a,int b){
        return distance[a][b]/40;
    }

    public static double getPublicCost(int a,int b){
        return 0.79+0.04*distance[a][b];
    }

    public static double getTaxiCost(int a,int b){ return 2.2+0.8*(distance[a][b]-1); }

    public static String getAttractionNames(int a) {
        return attractionNames[a];
    }

    public static int getAttractionId(String a){
        for (int i = 1; i < attractionNames.length; i++){
            if(a.equals(attractionNames[i])){
                return i;
            }
        }
        return 0;
    }



    public static int getNumberOfAttractions(){
        return attractionNames.length-1;
    }
}
