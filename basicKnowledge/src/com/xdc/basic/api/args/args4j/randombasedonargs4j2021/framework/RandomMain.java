package com.xdc.basic.api.args.args4j.randombasedonargs4j2021.framework;

public class RandomMain
{
    public static void main(String[] args)
    {
        args = new String[] { "arg1", "-c", "10", "-m", "custom", "arg2", "-s", "xdc0209", "arg3" };

        try
        {
            Command command = new RandomCommand();
            command.parseAndExec(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
