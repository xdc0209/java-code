package com.roadrantz.mvc;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

/**
 * Tests RantsForVehicleController
 * 
 * From Listing B.1
 * 
 * @author wallsc
 */
public class RantsForVehicleControllerTest extends TestCase {
   private static final String TEST_STATE = "TX";
   private static final String TEST_PLATE_NUMBER = "ABC123";
   private RantsForVehicleController controller;

   public RantsForVehicleControllerTest() {}

   /**
    * Setups up the test with a mock rant service.
    * 
    * From Listing B.2
    */
   @Override
   protected void setUp() throws Exception {
      controller = new RantsForVehicleController();
      controller.setCommandClass(Vehicle.class);
      RantService rantService = createMock(RantService.class);
      Vehicle testVehicle = new Vehicle();
      testVehicle.setState(TEST_STATE);
      testVehicle.setPlateNumber(TEST_PLATE_NUMBER);
      List<Rant> expectedRants = new ArrayList<Rant>();
      Rant rant = new Rant();
      rant.setVehicle(testVehicle);
      rant.setRantText("Rant 1");
      expectedRants.add(rant);
      rant = new Rant();
      rant.setVehicle(testVehicle);
      rant.setRantText("Rant 2");
      expectedRants.add(rant);
      expect(rantService.getRantsForVehicle(testVehicle)).andReturn(
                        expectedRants);
      replay(rantService);
      controller.setRantService(rantService);
   }

   @Override
   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testSimpleCase() throws Exception {
      MockHttpServletRequest request = new MockHttpServletRequest();
      request.setMethod("POST");
      request.addParameter("state", TEST_STATE);
      request.addParameter("plateNumber", TEST_PLATE_NUMBER);
      request
                        .setRequestURI("http://localhost:8080/roadrantz/rantsForVehicle.htm");
      HttpServletResponse response = new MockHttpServletResponse();

      ModelAndView modelAndView = controller.handleRequest(request, response);
      assertNotNull("ModelAndView should not be null", modelAndView);
      assertEquals("View name should be 'vehicleRants'", "vehicleRants",
                        modelAndView.getViewName());
      Map model = modelAndView.getModel();
      assertTrue("Model should contain 'rants' key", model.containsKey("rants"));
      List rants = (List) model.get("rants");
      assertNotNull("Model element 'rants' should not be null", rants);
      assertEquals("Model element 'rants' should contain 2 items", 2, rants
                        .size());
      for (Iterator iter = rants.iterator(); iter.hasNext();) {
         Rant rant = (Rant) iter.next();
         assertEquals("Rant's Vehicle state is incorrect", TEST_STATE, rant
                           .getVehicle().getState());
         assertEquals("Rant's Vehicle plateNumber is incorrect",
                           TEST_PLATE_NUMBER, rant.getVehicle()
                                             .getPlateNumber());
      }
   }
}