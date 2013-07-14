package com.roadrantz.tiles;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.springframework.web.servlet.
           view.tiles.ComponentControllerSupport;
import com.roadrantz.domain.Motorist;
import com.roadrantz.service.RantService;

public class HeaderTileController extends ComponentControllerSupport {
  public HeaderTileController() {}
  
  protected void doPerform(ComponentContext componentContext, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    RantService rantService = getRantService();
    
    int rantsToday = rantService.getRantsForDay(new Date()).size();
    componentContext.putAttribute("rantsToday", rantsToday);
    
    String state = "tx";
    Motorist driver = (Motorist)request.getSession().getAttribute("driver");
    if(driver != null) {
      state = driver.getVehicles().get(0).getState();
    }    
    componentContext.putAttribute("driverState", state);
  }
  
  private RantService getRantService() {
    return (RantService) getApplicationContext().getBean("rantService");
  }
}
