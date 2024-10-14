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
