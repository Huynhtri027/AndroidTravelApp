package com.sutd.zhangzhexian.travelapp.algorithm.model;

/**
 * Created by zhanghao on 11/11/15.
 */
public class AttractionList implements Cloneable{
    public boolean[] visited;

    public AttractionList(int length){
        visited=new boolean[length];
        for (int i = 0; i < this.visited.length; i++) {
            this.visited[i]=false;
        }
    }

    public AttractionList(){}

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
