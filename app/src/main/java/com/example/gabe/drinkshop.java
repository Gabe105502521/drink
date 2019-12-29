package com.example.gabe;

import java.util.HashMap;

public class drinkshop implements Comparable<drinkshop>{
    protected String name;
    protected Double lat;
    protected Double lng;
    protected String vicinity;
    protected String distance;
    protected Double rating;
    protected HashMap<String , String> drinks;

    public drinkshop(String name, Double lat, Double lng,  String vicinity, Double rating, String distance) {
        super();
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.vicinity = vicinity;
        this.rating = rating;
        this.distance = distance;
    }

    public double getRating() {
        return this.rating;
    }
    @Override
    public int compareTo(drinkshop o) {
        if (this.getRating()>o.getRating()){  //由大到小
            return -1;
        }else{
            return 1;
        }
    }
    //protected String opening_hours;
}
