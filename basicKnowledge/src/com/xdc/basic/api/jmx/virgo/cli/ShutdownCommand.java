package com.xdc.basic.api.jmx.virgo.cli;

import java.util.List;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.xdc.basic.api.jmx.virgo.cli.core.Option;

public class ShutdownCommand extends AbstractJmxCommand
{
    // 立即关闭，可选，不带参数
    private Option immediateOption = new Option("-i", false, false, "shutdown virgo immediate");

    @Override
    protected void initOptions(Map<String, Option> options)
    {
        options.put(immediateOption.getOption(), immediateOption);
    }

    @Override
    protected void excute(Map<String, String> parsedOptions, List<String> parsedArguments)
    {
        try
        {
            MBeanServerConnection mbsc = getBeanServerConnection();

            boolean immediate = parsedOptions.containsKey(immediateOption.getOption());

            ObjectName shutdownObjectName = new ObjectName("org.eclipse.virgo.kernel:type=Shutdown");

            if (immediate)
            {
                mbsc.invoke(shutdownObjectName, "immediateShutdown", null, null);
            }
            else
            {
                mbsc.invoke(shutdownObjectName, "shutdown", null, null);
            }
        }
        catch (Exception e)
        {
            System.err.println("ERROR: Invoke jmx func failed.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
