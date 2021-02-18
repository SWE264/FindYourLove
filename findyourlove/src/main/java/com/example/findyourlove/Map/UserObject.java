package com.example.findyourlove.Map;

public class UserObject {
    double distance;
    String userName;
    String gender;
    double latitute;
    double longitude;
    int accid;


    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAccid() {
        return accid;
    }

    public void setAccid(int accid) {
        this.accid = accid;
    }

    public UserObject(double distance, String userName, String gender, double latitute, double longitude, int accid) {
        this.distance = distance;
        this.userName = userName;
        this.gender = gender;
        this.latitute=latitute;
        this.longitude=longitude;
        this.accid=accid;
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
