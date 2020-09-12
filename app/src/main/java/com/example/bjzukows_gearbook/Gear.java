package com.example.bjzukows_gearbook;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gear {
    // Model class for Gear object
    private String gearDate;
    private String gearMaker;
    private String gearDescription;
    private double gearPrice;
    private String gearComment;

    public Gear(String date, String maker, String description, double price, String comment) {
        gearDate = date;
        gearMaker = maker;
        gearDescription = description;
        gearPrice = price;
        gearComment = comment;
    }

    // Getters and Setters for Gear attributes
    public String getGearDate() {
        return gearDate;
    }

    public void setGearDate(String gearDate) {
        this.gearDate = gearDate;
    }

    public String getGearMaker() {
        return gearMaker;
    }

    public void setGearMaker(String gearMaker) {
        this.gearMaker = gearMaker;
    }

    public String getGearDescription() {
        return gearDescription;
    }

    public void setGearDescription(String gearDescription) {
        this.gearDescription = gearDescription;
    }

    public double getGearPrice() {
        return gearPrice;
    }

    public void setGearPrice(Float gearPrice) {
        this.gearPrice = gearPrice;
    }

    public String getGearComment() {
        return gearComment;
    }

    public void setGearComment(String gearComment) {
        this.gearComment = gearComment;
    }
}
