package com.roadrantz.service;

import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * Tests the RantService from the Spring context along with its dependencies.
 * Strictly speaking, this is an integration test, not a unit-test, as it tests
 * the service and its dependencies, as wired in Spring.
 * 
 * Listing B.3
 * 
 * @author wallsc
 */
public class RantServiceTest extends
                  AbstractDependencyInjectionSpringContextTests {
   public RantServiceTest() {}

   @Override
   protected String[] getConfigLocations() {
      return new String[] { "roadrantz-services.xml", "roadrantz-data.xml",
            "roadrantz-data-jdbc.xml", "roadrantz-email.xml" };
   }

   public void testAddRant() throws Exception {
      RantService rantService = (RantService) applicationContext
                        .getBean("rantService");
      Rant newRant = new Rant();
      newRant.setRantText("TEST RANT TEXT");
      Vehicle vehicle = new Vehicle();
      vehicle.setPlateNumber("ABC123");
      vehicle.setState("TX");
      newRant.setVehicle(vehicle);
      rantService.addRant(newRant);
      List<Rant> rants = rantService.getRantsForVehicle(vehicle);
      assertTrue(rants.contains(newRant));
   }
}