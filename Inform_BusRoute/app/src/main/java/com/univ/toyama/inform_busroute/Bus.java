package com.univ.toyama.inform_busroute;

import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class Bus {
    @PropertyName("route")
    private ArrayList<String> route;

    @PropertyName("city")
    private ArrayList<String> city;

    @PropertyName("line")
    private ArrayList<String> line;

    @PropertyName("place")
    private ArrayList<String> place;

    @PropertyName("time")
    private ArrayList<String> time;

    @PropertyName("direction")
    private ArrayList<String> direction;

    public Bus() {
    }

    @PropertyName("city")
    public ArrayList<String> getCity() {
        return city;
    }

    @PropertyName("line")
    public ArrayList<String> getLine() {
        return line;
    }

    @PropertyName("place")
    public ArrayList<String> getPlace() {
        return place;
    }

    @PropertyName("time")
    public ArrayList<String> getTime() {
        return time;
    }

    @PropertyName("direction")
    public ArrayList<String> getDirection(){return direction;}
}