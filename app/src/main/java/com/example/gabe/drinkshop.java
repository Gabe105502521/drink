package com.example.gabe;

import android.os.AsyncTask;

public class drinkshop implements Comparable<drinkshop>{
    protected String name;
    protected Double lat;
    protected Double lng;
    protected String vicinity;
    protected Double rating;

    public drinkshop(String name, Double lat, Double lng,  String vicinity, Double rating) {
        super();
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.vicinity = vicinity;
        this.rating = rating;

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
