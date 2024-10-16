package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    // Declaration of properties
    private String description;
    private String vendor;
    private float amount;
    private LocalDate date;
    private LocalTime time;

    //Constructor
    public Transaction(float amount, LocalDate date, String description, LocalTime time, String vendor) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.time = time;
        this.vendor = vendor;


    }

    //Constructor accept a string array as input then it parses each element to the correct data type and sets the value of the variables.
    public Transaction(String line) {
        String[] properties = line.split ("\\|"); //
        this.amount = Float.parseFloat(properties[4]);
        this.date = LocalDate.parse(properties[0]);
        this.description = properties[2]; // setting the value of description as the third element of properties array
        this.time = LocalTime.parse(properties[1]);
        this.vendor = properties[3]; // setting the value of description as the fourth element of properties array

    }

    //new
    /*public Transaction(String properties) {
        this(properties.split("\\|")); // calling the constructor method

    } */

    @Override // ToStringMethod
    public String toString() {
        return "Date: " + this.date + "|" + "Time: " + this.time + "|" + "Vendor: " + this.vendor + "|" + "Amount:" + this.amount + "|" + "Description: " + this.description;

    }


    //Getters and setters

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
