package com.scmuWateringSystem.wateringSystem.application.Models;

public class Metric {
    private String date;
    private String value;

    public Metric(String date, String value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }


    public String getValue() {
        return value;
    }

}
