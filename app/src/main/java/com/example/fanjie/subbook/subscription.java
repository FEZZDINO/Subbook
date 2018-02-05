package com.example.fanjie.subbook;

/**
 * Created by fanjie on 18-2-5.
 */

public class subscription {
    private String name;
    private String date;
    private float price;
    private String comment;
    public  subscription(String name, String date, String price, String comment){
        this.name = name;
        this.date = date;
        this.price = Float.parseFloat(price);;
        this.comment = comment;
    }

    public String getName(){return this.name;}
    public String getDate(){return this.date;}
    public float getPrice(){return this.price;}
    public String getComments(){return this.comment;}

    public void setSub(String name, String date, String price, String comment){
        this.name = name;
        this.date = date;
        this.price = Float.parseFloat(price);;
        this.comment = comment;
    }





}
