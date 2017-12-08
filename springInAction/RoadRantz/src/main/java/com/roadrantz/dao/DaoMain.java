package com.roadrantz.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

public class DaoMain {
  public static void main(String[] args) {
    ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[] {
        "src/main/webapp/WEB-INF/services.xml", "src/main/webapp/WEB-INF/dataaccess.xml"   
    });
    
    RantService dao = (RantService) ctx.getBean("rantService");
    
    Rant rant = new Rant();
    rant.setPostedDate(new Date());
    rant.setRantText("TEST RANT 3");
    Vehicle vehicle = new Vehicle();
    vehicle.setPlateNumber("J55DNY");
    vehicle.setState("TX");
    rant.setVehicle(vehicle);
    dao.addRant(rant);

    rant = new Rant();
    rant.setPostedDate(new Date());
    rant.setRantText("TEST RANT 4");
    vehicle = new Vehicle();
    vehicle.setPlateNumber("G44MNX");
    vehicle.setState("TX");
    rant.setVehicle(vehicle);
    dao.addRant(rant);

  }
}
