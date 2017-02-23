package com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class NegotiateSale implements Command
{
    @Override
    public boolean execute(Context ctx) throws Exception
    {
        System.out.println("Negotiate sale");
        return false;
    }
}
