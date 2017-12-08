package com.roadrantz.mvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

public class RantsForVehicleController extends AbstractCommandController {
  private static final String BASE_VIEW_NAME = "vehicleRants";
  
  public RantsForVehicleController() {
    setCommandClass(Vehicle.class);
    setCommandName("vehicle");
  }
  
  protected ModelAndView handle(HttpServletRequest request, 
      HttpServletResponse response, Object command, 
      BindException errors) throws Exception {
    
    Vehicle vehicle = (Vehicle) command;

    Map model = errors.getModel();
    model.put("rants", rantService.getRantsForVehicle(vehicle));
    model.put("vehicle", vehicle);
    
    return new ModelAndView(getViewName(request), model);
  }
  
  private String getViewName(HttpServletRequest request) {
    String requestUri = request.getRequestURI();
    String extension = "." + requestUri.substring(requestUri.length() - 3);
    if(".htm".equals(extension)) { extension=""; }
    return BASE_VIEW_NAME + extension;
  }
  
  // injected
  private RantService rantService;
  public void setRantService(RantService rantService) {
    this.rantService = rantService;
  }
}
