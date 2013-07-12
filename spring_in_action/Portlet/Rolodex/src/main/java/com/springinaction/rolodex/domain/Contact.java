package com.springinaction.rolodex.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@SuppressWarnings("serial")
public class Contact implements Serializable{
  private Integer id;
  private String lastName;
  private String firstName;
  private String address1;
  private String address2;
  private String city;
  private String state;
  private String zipCode;
  private String phone1;
  private String phone2;
  private String fax;
  private String email;
  private String ownerName;

  public Contact() {}
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  

  @Column(length=30)
  public String getAddress1() {
    return address1;
  }
  
  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  @Column(length=30)
  public String getAddress2() {
    return address2;
  }
  
  public void setAddress2(String address2) {
    this.address2 = address2;
  }
  
  @Column(length=30)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
  
  @Column(length=50)
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  @Column(length=15)
  public String getFax() {
    return fax;
  }
  
  public void setFax(String fax) {
    this.fax = fax;
  }
  
  @Column(length=30)
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(length=30)
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(length=15)
  public String getPhone1() {
    return phone1;
  }
  
  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }
  
  
  @Column(length=15)
  public String getPhone2() {
    return phone2;
  }
  
  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }
  
  @Column(length=2)
  public String getState() {
    return state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  @Column(length=10)
  public String getZipCode() {
    return zipCode;
  }
  
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
  
  @Column(length=20)
  public String getOwnerName() {
    return ownerName;
  }
  
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }
}
