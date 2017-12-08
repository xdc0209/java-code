package com.springinaction.pizza.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable {
  private Integer id;
  private String name;
  private String streetAddress;
  private String city;
  private String state;
  private String zipCode;
  private String phoneNumber;
  private boolean inDeliveryArea;
  
  public Customer() {}

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public boolean isInDeliveryArea() {
    return inDeliveryArea;
  }

  public void setInDeliveryArea(boolean inDeliveryArea) {
    this.inDeliveryArea = inDeliveryArea;
  }
  
  
}
