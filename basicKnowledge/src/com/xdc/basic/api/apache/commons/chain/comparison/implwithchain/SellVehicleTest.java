package com.xdc.basic.api.apache.commons.chain.comparison.implwithchain;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;

import com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command.ArrangeFinancing;
import com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command.CloseSale;
import com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command.GetCustomerInfo;
import com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command.NegotiateSale;
import com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command.TestDriveVehicle;

public class SellVehicleTest
{
    public static void main(String[] args) throws Exception
    {
        Chain chain = new ChainBase();

        chain.addCommand(new GetCustomerInfo());
        chain.addCommand(new TestDriveVehicle());
        chain.addCommand(new NegotiateSale());
        chain.addCommand(new ArrangeFinancing());
        chain.addCommand(new CloseSale());

        Context ctx = new ContextBase();
        chain.execute(ctx);
    }
}
