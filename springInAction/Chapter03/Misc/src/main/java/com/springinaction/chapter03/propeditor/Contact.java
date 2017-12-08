package com.springinaction.chapter03.propeditor;

public class Contact {
  // This class will probably have other properties, too
  // But the phoneNumber property is all that's needed
  // to demonstrate property editors.
  
  private PhoneNumber phoneNumber;

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
