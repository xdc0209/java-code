package com.xdc.basic.api.jmx.virgo.client;

public class VirgoMain
{
    public static void main(String[] args)
    {
        // args = new String[] { "bundle", "-n", "org.eclipse.virgo.kernel", "-v", "3.6.2", "-s", "active", "-t" };

        try
        {
            VirgoCommand virgoCommand = new VirgoCommand();
            virgoCommand.parseAndExec(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
