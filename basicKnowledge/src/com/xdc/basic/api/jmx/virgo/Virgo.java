package com.xdc.basic.api.jmx.virgo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang3.StringUtils;

public class Virgo
{
    public static void main(String[] args) throws IOException, InstanceNotFoundException, MBeanException,
            ReflectionException, MalformedObjectNameException, IntrospectionException, AttributeNotFoundException
    {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.227.135:9875/jmxrmi");

        Map<String, Object> env = new HashMap<String, Object>();
        env.put(JMXConnector.CREDENTIALS, new String[] { "admin", "springsource" });

        JMXConnector jmxc = JMXConnectorFactory.connect(url, env);

        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        printPlanState(mbsc);

        printBundleState(mbsc);

    }

    private static void printPlanState(MBeanServerConnection mbsc) throws IOException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException
    {
        System.out.printf("%-12s%s\n", "State", "Plan");
        Set<ObjectName> queryNames = mbsc.queryNames(null, null);
        for (ObjectName objectName : queryNames)
        {
            if (StringUtils.containsIgnoreCase(objectName.getCanonicalName(), "artifact-type=plan"))
            {
                Object name = mbsc.getAttribute(objectName, "Name");
                Object state = mbsc.getAttribute(objectName, "State");
                Object version = mbsc.getAttribute(objectName, "Version");
                System.out.printf("%-12s%s_%s\n", state, name, version);
            }
        }
        System.out.println();
    }

    private static void printBundleState(MBeanServerConnection mbsc) throws MalformedObjectNameException,
            InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {

        ObjectName kernelObjectName = new ObjectName(
                "osgi.core:version=1.5,type=bundleState,region=org.eclipse.equinox.region.kernel");
        ObjectName userObjectName = new ObjectName(
                "osgi.core:version=1.5,type=bundleState,region=org.eclipse.virgo.region.user");

        Object listKernelBundlesResult = mbsc.invoke(kernelObjectName, "listBundles", null, null);
        Object listUserBundlesResult = mbsc.invoke(userObjectName, "listBundles", null, null);

        if (listKernelBundlesResult instanceof TabularData && listUserBundlesResult instanceof TabularData)
        {
            TabularData kernelTabularData = (TabularData) listKernelBundlesResult;
            TabularData userTabularData = (TabularData) listUserBundlesResult;

            System.out.println("==== Kernel Bundles State ====");
            System.out.printf("%-5s%-12s%s\n", "id", "State", "Bundle");
            for (Object o : kernelTabularData.values())
            {
                if (o instanceof CompositeData)
                {
                    CompositeData compositeData = (CompositeData) o;

                    Object identifier = compositeData.get("Identifier");
                    Object state = compositeData.get("State");
                    Object symbolicName = compositeData.get("SymbolicName");
                    Object version = compositeData.get("Version");
                    System.out.printf("%-5s%-12s%s_%s\n", identifier, state, symbolicName, version);
                }
            }
            System.out.println();

            System.out.println("==== User Bundles State ====");
            System.out.printf("%-5s%-12s%s\n", "id", "State", "Bundle");
            for (Object o : userTabularData.values())
            {
                if (o instanceof CompositeData)
                {
                    CompositeData compositeData = (CompositeData) o;

                    Object identifier = compositeData.get("Identifier");
                    Object state = compositeData.get("State");
                    Object symbolicName = compositeData.get("SymbolicName");
                    Object version = compositeData.get("Version");
                    System.out.printf("%-5s%-12s%s_%s\n", identifier, state, symbolicName, version);
                }
            }
            System.out.println();
        }
    }
}
