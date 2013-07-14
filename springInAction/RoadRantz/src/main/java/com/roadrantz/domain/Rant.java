package com.roadrantz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SuppressWarnings("serial")
public class Rant implements Serializable{
  private Integer id;
  private Vehicle vehicle;
  private String rantText;
  private Date postedDate;
  
  public Rant() {}

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRantText() {
    return rantText;
  }

  public void setRantText(String rantText) {
    this.rantText = rantText;
  }

  @Temporal(TemporalType.DATE)
  public Date getPostedDate() {
    return postedDate;
  }

  public void setPostedDate(Date postedDate) {
    this.postedDate = postedDate;
  }

  @ManyToOne
  public Vehicle getVehicle() {
    return vehicle;
  }

  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }
  
  public boolean equals(Object o) {
    if (o instanceof Rant) {
      Rant rant = (Rant) o;
      return rant.id.equals(this.id);
    } else {
      return false;
    }
  }
  
  public int hashCode() {
    // TODO Auto-generated method stub
    return super.hashCode();
  }
}
