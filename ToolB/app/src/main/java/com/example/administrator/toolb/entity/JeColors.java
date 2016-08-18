package com.example.administrator.toolb.entity;

/**
 * Created by Administrator on 2016/7/20.
 */
public class JeColors {
    private String name;
    private String place;

    public JeColors(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "JeColors{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
