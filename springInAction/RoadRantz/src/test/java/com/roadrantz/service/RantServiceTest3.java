package com.roadrantz.service;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * Tests the RantService from the Spring context along with its dependencies.
 * Strictly speaking, this is an integration test, not a unit-test, as it tests
 * the service and its dependencies, as wired in Spring.
 * 
 * This version adds transactional support to the test, ensuring that any
 * changes to the DB get rolled back after each test (assuming that the DB
 * supports rollbacks).
 * 
 * Listing B.5
 * 
 * @author wallsc
 */
public class RantServiceTest3 extends AbstractTransactionalDataSourceSpringContextTests
{
    public RantServiceTest3()
    {
    }

    @Override
    protected String[] getConfigLocations()
    {
        return new String[] { "roadrantz-services.xml", "roadrantz-data.xml", "roadrantz-data-jdbc.xml",
                "roadrantz-email.xml" };
    }

    public void testAddRant() throws Exception
    {
        RantService rantService = (RantService) applicationContext.getBean("rantService");
        Rant newRant = new Rant();
        newRant.setRantText("TEST RANT TEXT");
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("FOOBAR");
        vehicle.setState("TX");
        newRant.setVehicle(vehicle);
        int before = jdbcTemplate.queryForInt("select count(*) from rant");
        rantService.addRant(newRant);
        int after = jdbcTemplate.queryForInt("select count(*) from rant");
        assertEquals("There should be one more row in rant table.", after, before + 1);
        String testRantText = (String) jdbcTemplate.queryForObject("select rantText from rant where id=?",
                new Object[] { newRant.getId() }, String.class);
        assertEquals("TEST RANT TEXT", testRantText);
    }
}
