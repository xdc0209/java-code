package com.roadrantz.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.roadrantz.service.RantService;

public class OldRantsForDayController extends AbstractCommandController {
  public OldRantsForDayController() {
    setCommandClass(DayForm.class);
  }
  
  protected ModelAndView handle(HttpServletRequest request, 
      HttpServletResponse response, Object command, 
      BindException errors) throws Exception {

    DayForm day = (DayForm) command;
    
    return new ModelAndView("dayRants", "rants", 
        rantService.getRantsForDay(day.getDay()));
  }
  
  // injected
  private RantService rantService;
  public void setRantService(RantService rantService) {
    this.rantService = rantService;
  }
}
