package com.xdc.basic.api.jmx.virgo.client;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.xdc.basic.api.jmx.virgo.client.config.JmxConfig;
import com.xdc.basic.api.jmx.virgo.client.core.AbstractAtomCommand;

public abstract class AbstractJmxCommand extends AbstractAtomCommand
{
    protected MBeanServerConnection getBeanServerConnection()
    {
        MBeanServerConnection mbsc = null;
        try
        {
            JMXServiceURL url = new JMXServiceURL(String.format(JmxConfig.getUrl(), JmxConfig.getIp(),
                    JmxConfig.getPort()));

            Map<String, Object> env = new HashMap<String, Object>();
            env.put(JMXConnector.CREDENTIALS, new String[] { JmxConfig.getUser(), JmxConfig.getPassword() });

            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);

            mbsc = jmxc.getMBeanServerConnection();
        }
        catch (Exception e)
        {
            System.err.println("ERROR: The Server could not be reached, it may already be stopped.");
            e.printStackTrace();
            System.exit(1);
        }
        return mbsc;
    }
}
