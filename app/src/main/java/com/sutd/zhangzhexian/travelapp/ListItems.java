package com.sutd.zhangzhexian.travelapp;

/**
 * Created by Lakshita on 11/14/2015.
 */
public class ListItems {
    private String Place;
    private String Method;

    public ListItems(String plc, String mode){
        Place = plc;
        Method = mode;
    }

    public void setPlace(String place){
        Place = place;
    }
    public void setMethod(String method){
        Method = method;
    }

    public String getPlace(){
        return Place;
    }
    public String getMethod(){
        return Method;
    }

}
