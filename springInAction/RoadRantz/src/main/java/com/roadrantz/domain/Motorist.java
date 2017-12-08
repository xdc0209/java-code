package com.roadrantz.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SuppressWarnings("serial")
public class Motorist implements Serializable {
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<Vehicle> vehicles;
  private Set<MotoristPrivilege> privileges;
  
  public Motorist() {
    privileges = new HashSet<MotoristPrivilege>();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @OneToMany(cascade=CascadeType.ALL, 
      mappedBy="motorist")
  public List<Vehicle> getVehicles() {
    return vehicles;
  }

  public void setVehicles(List<Vehicle> vehicles) {
    this.vehicles = vehicles;
  }
  
  @Transient
  public List<Rant> getRants() {
    List<Rant> allRants = new ArrayList<Rant>();
    
    for (Vehicle vehicle : vehicles) {
      allRants.addAll(vehicle.getRants());
    }
    
    return allRants;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @OneToMany(cascade=CascadeType.ALL, 
      fetch=FetchType.LAZY, 
      mappedBy="motorist")
  public Set<MotoristPrivilege> getPrivileges() {
    return privileges;
  }
  
  public void setPrivileges(Set<MotoristPrivilege> privileges) {
    this.privileges = privileges;
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
