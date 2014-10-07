package com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class CloseSale implements Command
{
    @Override
    public boolean execute(Context ctx) throws Exception
    {
        System.out.println("Congratulations " + ctx.get("customerName") + ", you bought a new car!");
        return false;
    }
}