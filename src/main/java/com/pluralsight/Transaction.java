package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

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

    //TODO I got help from Sameem to create this Constructor
    //TODO The constructor is accepting a string as input, splinting the line of the file that is stored in a String Array using pipe as a delimiter (transaction csv file) and parsing to the correct data type.
    public Transaction(String transactionLine) { //Transaction line string array temporary stores the transaction line
        String[] properties = transactionLine.split("\\|"); //Splitting the line with a pipe delimiter
        this.amount = Float.parseFloat(properties[4]);
        this.date = LocalDate.parse(properties[0]);
        this.description = properties[2]; // setting the value of description as the third element of properties array
        this.time = LocalTime.parse(properties[1]);
        this.vendor = properties[3]; // setting the value of description as the fourth element of properties array
    }
     //I got help from Sameem to create this Method
    //Method to show transaction details in a clean format
    public String showDetails() {
        return "Date: " + this.date + "|" + "Time: " + this.time + "|" + "Description: " + this.description + "|" + "Vendor: " + this.vendor + "|" + "Amount:" + this.amount ;

    } //Help ends here


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
