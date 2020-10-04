package com.example.imageupload;

import com.google.firebase.database.Exclude;

public class Prescription {
    private Integer ID;
    private String location;
    private String number;
    private String url;
    private String key;

    public Prescription(){

    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getID() {
        return ID;
    }

    public String getLocation() {
        return location;
    }

    public String getNumber() {
        return number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
