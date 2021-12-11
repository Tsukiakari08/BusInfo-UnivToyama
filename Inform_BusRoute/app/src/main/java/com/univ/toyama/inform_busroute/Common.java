package com.univ.toyama.inform_busroute;

import android.app.Application;
import java.util.List;

public class Common extends Application{
    private String prefecture_name;
    private String city_name;
    private String line_name;
    private String place_name;
    private String direction;
    private String day;

    private List<String> city_list;
    private List<String> line_list;
    private List<String> place_list;
    private List<String> time_list;
    private List<String> direction_list;

    private int state = 0;
    private String current_str = "";


    public void initCurrent_str(){current_str = "";}

    //setter
    public void setPrefecture_name(String prefecture_name) { this.prefecture_name = prefecture_name; }

    public void setCity_name(String city_name) { this.city_name = city_name; }

    public void setLine_name(String line_name) { this.line_name = line_name; }

    public void setPlace_name(String place_name) { this.place_name = place_name; }

    public void setCity_list(List<String> city_list) { this.city_list = city_list; }

    public void setLine_list(List<String> line_list) { this.line_list = line_list; }

    public void setPlace_list(List<String> place_list) { this.place_list = place_list; }

    public void setTime_list(List<String> time_list) { this.time_list = time_list; }

    public void setDirection_list(List<String> direction_list) { this.direction_list = direction_list; }

    public void setDirection(String direction) { this.direction = direction; }

    public void setState(int state) { this.state = state; }

    public void setCurrent_str(String current_str) { this.current_str = current_str; }

    public void setDay(String day) { this.day = day; }

    //getter
    public String getPrefecture_name(){ return prefecture_name; }

    public String getCity_name(){ return city_name; }

    public String getLine_name(){ return line_name; }

    public String getPlace_name(){ return place_name; }

    public List<String> getCity_list(){ return city_list; }

    public List<String> getLine_list(){ return  line_list; }

    public List<String> getPlace_list(){ return  place_list; }

    public List<String> getTime_list() { return time_list; }

    public List<String> getDirection_list() { return direction_list; }

    public int getState(){ return state; }

    public String getDirection() {return direction; }

    public String getCurrent_str() { return current_str; }

    public String getDay() { return day; }
}
