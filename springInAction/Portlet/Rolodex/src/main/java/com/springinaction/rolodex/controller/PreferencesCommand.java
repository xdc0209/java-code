package com.springinaction.rolodex.controller;

import java.io.Serializable;


public class PreferencesCommand implements Serializable {
  public static final String DEFAULT_PAGE_SIZE = "5";
  
  public PreferencesCommand() {}
  
  private int pageSize;
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  
  public int getPageSize() {
    return pageSize;
  }
}
