package com.springinaction.rolodex.controller;

public class SearchCommand {
  private String firstName;
  private String lastName;
  
  public SearchCommand() {}

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
