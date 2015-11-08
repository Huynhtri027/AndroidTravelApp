package com.sutd.zhangzhexian.travelapp.database;

/**
 * Created by zhanghao on 7/11/15.
 */

public class Data {
    private String[] attractionNames;
    public double[][] distance;
    public double[][] walkTime;
    public double[][] taxiTime;

    public Data(String[] attractionNames){
        this.attractionNames=attractionNames;
        this.attractionNames=this.sort(this.attractionNames);
        distance=new double[attractionNames.length][attractionNames.length];
        walkTime=new double[attractionNames.length][attractionNames.length];
        taxiTime=new double[attractionNames.length][attractionNames.length];
        for (int i = 0; i < attractionNames.length; i++) {
            this.distance[0][i]=(double)i;
            this.distance[i][0]=(double)i;
            this.walkTime[0][i]=(double)i;
            this.walkTime[i][0]=(double)i;
            this.taxiTime[0][i]=(double)i;
            this.taxiTime[i][0]=(double)i;
        }
        for (int i = 1; i < attractionNames.length; i++) {
            for (int j = 1; j < attractionNames.length; j++) {
                distance[i][j]=DataBase.getDistance(DataBase.getAttractionId(attractionNames[i]),
                        DataBase.getAttractionId(attractionNames[j]));
                walkTime[i][j]=DataBase.getWalkTime(DataBase.getAttractionId(attractionNames[i]),
                        DataBase.getAttractionId(attractionNames[j]));
                taxiTime[i][j] = DataBase.getTaxiTime(DataBase.getAttractionId(attractionNames[i]),
                        DataBase.getAttractionId(attractionNames[j]));
            }
        }
    }

    private String[] sort(String[] attractionNames){
        for (int i = 1; i < attractionNames.length; i++) {
            for (int j = i+1; j < attractionNames.length ; j++) {
                if (DataBase.getAttractionId(attractionNames[i])
                        >DataBase.getAttractionId(attractionNames[j])){
                    String temp=attractionNames[i];
                    attractionNames[i]=attractionNames[j];
                    attractionNames[j]=temp;
                }
            }
        }
        return attractionNames;
    }

    public  double getWalkTime(int a,int b){
        return walkTime[a][b];
    }

    public  double getPublicTime(int a, int b){
        return distance[a][b]/15*3600;
    }

    public double getTaxiTime(int a,int b){
        return taxiTime[a][b];
    }

    public  double getPublicCost(int a,int b){
        return 0.79+0.04*distance[a][b];
    }

    public double getTaxiCost(int a,int b){ return 2.2+0.8*(distance[a][b]-1); }


    public String getAttractionNames(int a) {
        return attractionNames[a];
    }

    public  int getNumberOfAttractions(){
        return attractionNames.length-1;
    }

    public double getDistance(int a,int b){
        return distance[a][b];
    }

    public int getAttractionId(String a){
        for (int i = 1; i < attractionNames.length; i++){
            if(a.equals(attractionNames[i])){
                return i;
            }
        }
        return 0;
    }





}