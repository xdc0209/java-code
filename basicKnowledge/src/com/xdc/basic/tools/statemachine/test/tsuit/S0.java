package com.xdc.basic.tools.statemachine.test.tsuit;

public class S0
{
    public static String onE1(ContextInfo context, E1Arg arg)
    {
        String item = "S0:" + arg.getArg();
        context.addItem(item);
        if (arg.getArg().startsWith("OK"))
        {
            return "OK";
        }
        else
        {
            return "ERR";
        }
    }

    public static int onE2(ContextInfo context, E2Arg arg)
    {
        String item = "S0:" + arg.getArg();
        context.addItem(item);
        if (arg.getArg().startsWith("OK"))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    public int onE2NoStatic(ContextInfo context, E2Arg arg)
    {
        return 0;
    }
}
