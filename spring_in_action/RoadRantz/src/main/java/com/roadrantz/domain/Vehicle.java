package com.roadrantz.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@SuppressWarnings("serial")
public class Vehicle implements Serializable {
  private Integer id;
  private String state;
  private String plateNumber;
  private Motorist motorist;
  private List<Rant> rants;
  
  public Vehicle() {}

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPlateNumber() {
    return plateNumber;
  }

  public void setPlateNumber(String plateNumber) {
    this.plateNumber = stripNonAlphanumeric(plateNumber);
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state.toUpperCase();
  }
  
  
  @ManyToOne
  public Motorist getMotorist() {
    return motorist;
  }
  
  public void setMotorist(Motorist motorist) {
    this.motorist = motorist;
  }

  @OneToMany(targetEntity=Rant.class, 
      cascade=CascadeType.ALL, 
      mappedBy="vehicle")
  public List<Rant> getRants() {
    return rants;
  }

  public void setRants(List<Rant> rants) {
    this.rants = rants;
  }
  
  private String stripNonAlphanumeric(String in) {
    if(in == null) { return null; }
    
    StringBuffer outBuffer = new StringBuffer(in.length());
    
    for(int i = 0; i < in.length(); i++) {
      char c = in.charAt(i);
      if(Character.isLetter(c) || Character.isDigit(c)) {
        outBuffer.append(Character.toUpperCase(c));
      }
    }
    
    return outBuffer.toString();
  }
  
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }
}
