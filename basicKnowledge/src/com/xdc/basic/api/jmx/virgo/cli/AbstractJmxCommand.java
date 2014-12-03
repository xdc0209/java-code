package com.xdc.basic.api.jmx.virgo.cli;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.xdc.basic.api.jmx.virgo.cli.core.AbstractAtomCommand;

public abstract class AbstractJmxCommand extends AbstractAtomCommand
{
    protected MBeanServerConnection getBeanServerConnection()
    {
        MBeanServerConnection mbsc = null;
        try
        {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.227.135:9875/jmxrmi");

            Map<String, Object> env = new HashMap<String, Object>();
            env.put(JMXConnector.CREDENTIALS, new String[] { "admin", "springsource" });

            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);

            mbsc = jmxc.getMBeanServerConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return mbsc;
    }
}
