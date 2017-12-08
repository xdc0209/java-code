package com.roadrantz.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;

import org.springframework.ejb.support.AbstractStatelessSessionBean;

import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.MotoristAlreadyExistsException;
import com.roadrantz.service.RantService;

public class RantServiceEjb 
    extends AbstractStatelessSessionBean 
    implements RantService {  
  public RantServiceEjb() {}

  private RantService rantService;
  protected void onEjbCreate() throws CreateException {
    rantService = (RantService) getBeanFactory().getBean("rantService");
  }
  
  public void addMotorist(Motorist motorist) 
      throws MotoristAlreadyExistsException {
    rantService.addMotorist(motorist);
  }
  
  public void addRant(Rant rant) {
    rantService.addRant(rant);
  }

  public List<Rant> getRantsForDay(Date date) {
    return rantService.getRantsForDay(date);
  }

  public List<Rant> getRantsForVehicle(Vehicle vehicle) {
    return rantService.getRantsForVehicle(vehicle);
  }

  public List<Rant> getRecentRants() {
    return rantService.getRecentRants();
  }

  public void sendDailyRantEmails() {
    rantService.sendDailyRantEmails();
  }

  public void sendEmailForVehicle(Vehicle vehicle) {
    rantService.sendEmailForVehicle(vehicle);
  }
}
