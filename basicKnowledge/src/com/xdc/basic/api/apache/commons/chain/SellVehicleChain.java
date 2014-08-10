package com.xdc.basic.api.apache.commons.chain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;

public class SellVehicleChain extends ChainBase
{

    public SellVehicleChain()
    {
        super();
        addCommand(new GetCustomerInfo());
        addCommand(new TestDriveVehicle());
        addCommand(new NegotiateSale());
        addCommand(new ArrangeFinancing());
        addCommand(new CloseSale());
    }

    public static void main(String[] args) throws Exception
    {
        Command process = new SellVehicleChain();
        Context ctx = new ContextBase();
        process.execute(ctx);
    }

}