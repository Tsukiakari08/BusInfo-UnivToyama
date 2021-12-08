package com.univ.toyama.inform_busroute;

import android.app.Application;
import java.util.List;

public class Common extends Application{
    private String prefecture_name;
    private String city_name;
    private String line_name;
    private String place_name;
    private String direction;

    private List<String> city_list;
    private List<String> line_list;
    private List<String> place_list;
    private List<String> time_list;
    private List<String> direction_list;

    private int state = 0;
    private String current_str = "";


    public void initCurrent_str(){current_str = "";}

    //setter
    public void setPrefecture_name(String s){prefecture_name = s;}

    public void setCity_name(String s){city_name = s;}

    public void setLine_name(String s){line_name = s;}

    public void setPlace_name(String s){place_name = s;}

    public void setCity_list(List<String> list){city_list = list;}

    public void setLine_list(List<String> list){line_list = list;}

    public void setPlace_list(List<String> list){place_list = list;}

    public void setTime_list(List<String> list){time_list = list;}

    public void setDirection_list(List<String> list) {direction_list = list;}

    public void setState(int i){state = i;}

    public void setDirection(String str) {direction = str;}

    public void setCurrent_str(String str) {current_str = str;}

    //getter
    public String getPrefecture_name(){return prefecture_name;}

    public String getCity_name(){return city_name;}

    public String getLine_name(){return line_name;}

    public String getPlace_name(){return place_name;}

    public List<String> getCity_list(){return city_list;}

    public List<String> getLine_list(){return  line_list;}

    public List<String> getPlace_list(){return  place_list;}

    public List<String> getTime_list() {return time_list;}

    public List<String> getDirection_list() {return direction_list;}

    public int getState(){return state;}

    public String getDirection() {return direction;}

    public String getCurrent_str() {return current_str;}


}
