package com.springinaction.chapter03.propeditor;

public class PhoneNumber {
  private String areaCode;
  private String prefix;
  private String number;

  public PhoneNumber() { }

  public PhoneNumber(String areaCode, String prefix, 
                     String number) {
    this.areaCode = areaCode;
    this.prefix = prefix;
    this.number = number;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

}
