package com.roadrantz.marketing;

import java.io.Serializable;

public class SpammedMotorist implements Serializable {
  private String firstName;
  private String lastName;
  private String email;
  
  public SpammedMotorist() {}

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

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  
}
