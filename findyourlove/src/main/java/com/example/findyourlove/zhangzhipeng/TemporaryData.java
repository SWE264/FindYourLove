package com.example.findyourlove.zhangzhipeng;

public class TemporaryData {
    double distance;
    String userName;
    String gender;
    double latitute;
    double longitude;


    public TemporaryData(double distance, String userName, String gender,double latitute,double longitude) {
        this.distance = distance;
        this.userName = userName;
        this.gender = gender;
        this.latitute=latitute;
        this.longitude=longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
