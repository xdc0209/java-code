package com.roadrantz.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

public class MotoristRegistrationController extends AbstractWizardFormController {
  public MotoristRegistrationController() {
    setCommandClass(Motorist.class);
    setCommandName("motorist");
  }
  
  protected Object formBackingObject(HttpServletRequest request) 
      throws Exception {
    Motorist formMotorist = new Motorist();
    List<Vehicle> vehicles = new ArrayList<Vehicle>();
    vehicles.add(new Vehicle());
    formMotorist.setVehicles(vehicles);
    return formMotorist;
  }
  
  protected Map referenceData(HttpServletRequest request, 
      Object command, Errors errors, int page) throws Exception {
    
    Motorist motorist = (Motorist) command;
    Map refData = new HashMap();
    
    if(page == 1 && request.getParameter("_target1") != null) {
        refData.put("nextVehicle", motorist.getVehicles().size() - 1);
    }
    
    return refData;
  }
  
  protected void postProcessPage(HttpServletRequest request, 
      Object command, Errors errors, int page) throws Exception {
    
    Motorist motorist = (Motorist) command;
    
    if(page == 1 && request.getParameter("_target1") != null) {
      motorist.getVehicles().add(new Vehicle());
  }  }

  protected ModelAndView processFinish(HttpServletRequest request, 
      HttpServletResponse response, Object command, BindException errors) 
      throws Exception {
    
    Motorist motorist = (Motorist) command;
    
    // the last vehicle is always blank...remove it
    motorist.getVehicles().remove(motorist.getVehicles().size() - 1);
    
    rantService.addMotorist(motorist);
    
    return new ModelAndView(getSuccessView(), "motorist", motorist);
  }

  // injected
  private RantService rantService;
  public void setRantService(RantService rantService) {
    this.rantService = rantService;
  }
  
  // returns the last page as the success view
  private String getSuccessView() {
    return getPages()[getPages().length-1];
  }
}
