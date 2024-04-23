package com.example.task7_1;

import java.util.List;

public class Item {
    private String name;
    private String status;
    private String phone;
    private String description;
    private String date;
    private String location;

    public Item(String name, String status, String phone, String description, String date, String location){
        this.name = name;
        this.status = status;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public String toString() {
        return status + " " + name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
