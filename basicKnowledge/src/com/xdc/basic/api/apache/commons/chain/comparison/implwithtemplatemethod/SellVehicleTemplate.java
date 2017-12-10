package com.xdc.basic.api.apache.commons.chain.comparison.implwithtemplatemethod;

public abstract class SellVehicleTemplate
{
    public void sellVehicle()
    {
        getCustomerInfo();
        testDriveVehicle();
        negotiateSale();
        arrangeFinancing();
        closeSale();
    }

    public abstract void getCustomerInfo();

    public abstract void testDriveVehicle();

    public abstract void negotiateSale();

    public abstract void arrangeFinancing();

    public abstract void closeSale();
}
