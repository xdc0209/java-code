package com.roadrantz.struts;

import org.apache.struts.action.ActionForm;

import com.roadrantz.domain.Vehicle;

@SuppressWarnings("serial")
public class VehicleForm extends ActionForm
{
    public VehicleForm()
    {
    }

    private Vehicle vehicle;

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }
}
