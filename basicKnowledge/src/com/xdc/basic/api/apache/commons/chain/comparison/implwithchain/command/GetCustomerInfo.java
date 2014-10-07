package com.xdc.basic.api.apache.commons.chain.comparison.implwithchain.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class GetCustomerInfo implements Command
{
    @SuppressWarnings("unchecked")
    @Override
    public boolean execute(Context ctx) throws Exception
    {
        System.out.println("Get customer info");
        ctx.put("customerName", "George Burdell");
        return false;
    }
}