package com.roadrantz.webwork;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork.Action;
import com.roadrantz.domain.Rant;
import com.roadrantz.service.RantService;

public class RantsForDayAction implements Action {
  private int month;
  private int day;
  private int year;
  private List<Rant> rants;

  public String execute() throws Exception {
    // Yeah...I know...it's deprecated...sorry.
    // But I refuse to use the ridiculous Calendar class
    Date date = new Date(month, day, year);
    
    rants = rantService.getRantsForDay(date);
        
    return SUCCESS;
  }

  public List<Rant> getRants() {
    return rants;
  }
  
  public void setDay(int day) {
    this.day = day;
  }
  
  public void setMonth(int month) {
    this.month = month;
  }
  
  public void setYear(int year) {
    this.year = year;
  }

  private RantService rantService;
  public void setRantService(RantService rantService) {
    this.rantService = rantService;
  }
}
