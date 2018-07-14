package com.roadrantz.mvc;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.AbstractModelAndViewTests;
import org.springframework.web.servlet.ModelAndView;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;
import com.roadrantz.service.RantService;

/**
 * Tests RantsForVehicleController
 * 
 * Originally from Listing B.1, but tweaked to extend AbstractModelAndViewTest
 * on page 691.
 * 
 * @author wallsc
 */
public class RantsForVehicleControllerTest2 extends AbstractModelAndViewTests
{
    private static final String       TEST_STATE        = "TX";
    private static final String       TEST_PLATE_NUMBER = "ABC123";
    private RantsForVehicleController controller;

    public RantsForVehicleControllerTest2()
    {
    }

    private List<Rant> expectedRants;

    /**
     * Setups up the test with a mock rant service.
     * 
     * From Listing B.2
     */
    @Override
    protected void setUp() throws Exception
    {
        controller = new RantsForVehicleController();
        controller.setCommandClass(Vehicle.class);
        RantService rantService = createMock(RantService.class);
        Vehicle testVehicle = new Vehicle();
        testVehicle.setState(TEST_STATE);
        testVehicle.setPlateNumber(TEST_PLATE_NUMBER);
        expectedRants = new ArrayList<Rant>();
        Rant rant = new Rant();
        rant.setVehicle(testVehicle);
        rant.setRantText("Rant 1");
        expectedRants.add(rant);
        rant = new Rant();
        rant.setVehicle(testVehicle);
        rant.setRantText("Rant 2");
        expectedRants.add(rant);
        expect(rantService.getRantsForVehicle(testVehicle)).andReturn(expectedRants);
        replay(rantService);
        controller.setRantService(rantService);
    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testSimpleCase() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.addParameter("state", TEST_STATE);
        request.addParameter("plateNumber", TEST_PLATE_NUMBER);
        request.setRequestURI("http://localhost:8080/roadrantz/rantsForVehicle.htm");
        HttpServletResponse response = new MockHttpServletResponse();
        ModelAndView modelAndView = controller.handleRequest(request, response);
        Rant rant = new Rant();
        rant.setId(1);
        Vehicle vehicle = new Vehicle();
        vehicle.setState("TX");
        vehicle.setPlateNumber("ABC123");
        rant.setVehicle(vehicle);
        rant.setRantText("This is a test rant");
        assertNotNull("ModelAndView should not be null", modelAndView);
        assertViewName(modelAndView, "vehicleRants");
        assertModelAttributeAvailable(modelAndView, "rants");
        assertCompareListModelAttribute(modelAndView, "rants", expectedRants);
    }
}
