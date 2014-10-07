package com.xdc.basic.api.jmx;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxReflectionUtil
{
    private static final String LOCAL_CONNECTOR_ADDRESS = "com.sun.management.jmxremote.localConnectorAddress";
    private static final String MANAGEMENT_AGENT_JAR    = File.separator + "lib" + File.separator
                                                                + "management-agent.jar";

    // 以免类被多次加载引发错误，将此定义为静态字段，只加载一次。
    private static Class<?>     vmClazz                 = null;
    private static Class<?>     vmdClazz                = null;
    static
    {
        try
        {
            URLClassLoader loader = getToolsClassLoader();
            vmClazz = Class.forName("com.sun.tools.attach.VirtualMachine", true, loader);
            vmdClazz = Class.forName("com.sun.tools.attach.VirtualMachineDescriptor", true, loader);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String getJVM()
    {
        return System.getProperty("java.vm.specification.vendor");
    }

    private static boolean isSunJVM()
    {
        // need to check for Oracle as that is the name for Java7 onwards.
        return getJVM().equals("Sun Microsystems Inc.") || getJVM().startsWith("Oracle");
    }

    /**
     * @return map[key: vmId entry: displayName]
     * @throws ClassNotFoundException
     * @throws MalformedURLException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static Map<String, String> getAttachableVMs()
    {
        // 采用LinkedHashMap, 保持放入顺序
        Map<String, String> map = new LinkedHashMap<String, String>();

        try
        {
            Method vmListMethod = vmClazz.getMethod("list", (Class[]) null);
            Method vmdIdMethod = vmdClazz.getMethod("id", (Class[]) null);
            Method vmdDisplayNameMethod = vmdClazz.getMethod("displayName", (Class[]) null);

            List<?> AttachableVMs = (List<?>) vmListMethod.invoke(null, (Object[]) null);
            for (Object vmd : AttachableVMs)
            {
                String id = (String) vmdIdMethod.invoke(vmd, (Object[]) null);
                String displayName = (String) vmdDisplayNameMethod.invoke(vmd, (Object[]) null);
                map.put(id, displayName);
            }

            System.out.printf("%-8s%s\n", "pid", "displayName");
            for (Entry<String, String> entry : map.entrySet())
            {
                String id = entry.getKey();
                String displayName = entry.getValue();
                System.out.printf("%-8s%s\n", id, displayName);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return map;
    }

    public static String findJMXUrlByProcessId(String pid)
    {
        if (!isSunJVM())
        {
            System.err.println("Failed to get Local JMX Address by Pid for it is not sun jvm.");
            return null;
        }

        try
        {
            Method vmAttachMethod = vmClazz.getMethod("attach", String.class);
            Method vmDetachMethod = vmClazz.getMethod("detach", (Class[]) null);
            Method vmGetAgentPropertiesMethod = vmClazz.getMethod("getAgentProperties", (Class[]) null);
            Method vmGetSystemPropertiesMethod = vmClazz.getMethod("getSystemProperties", (Class[]) null);
            Method vmLoadAgentMethod = vmClazz.getMethod("loadAgent", String.class, String.class);

            Object vm = vmAttachMethod.invoke(null, pid);
            Properties agentProperties = (Properties) vmGetAgentPropertiesMethod.invoke(vm, (Object[]) null);
            String localJmxAddress = agentProperties.getProperty(LOCAL_CONNECTOR_ADDRESS);
            if (localJmxAddress != null)
            {
                vmDetachMethod.invoke(vm, (Object[]) null);
                return localJmxAddress;
            }

            Properties systemProperties = (Properties) vmGetSystemPropertiesMethod.invoke(vm, (Object[]) null);

            String vmJavaHome = systemProperties.getProperty("java.home");
            String agentPath = getAgentPath(vmJavaHome);

            vmLoadAgentMethod.invoke(vm, agentPath, "com.sun.management.jmxremote");

            agentProperties = (Properties) vmGetAgentPropertiesMethod.invoke(vm, (Object[]) null);
            localJmxAddress = agentProperties.getProperty(LOCAL_CONNECTOR_ADDRESS);
            if (localJmxAddress != null)
            {
                vmDetachMethod.invoke(vm, (Object[]) null);
                return localJmxAddress;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.err.println("Failed to get Local JMX Address by Pid.");
        return null;
    }

    // Normally in ${java.home}/jre/lib/management-agent.jar but might be in ${java.home}/lib in build environments.
    private static String getAgentPath(String javaHome) throws IOException
    {
        String agentPath = javaHome + File.separator + "jre" + MANAGEMENT_AGENT_JAR;
        File f = new File(agentPath);
        if (!f.exists())
        {
            agentPath = javaHome + MANAGEMENT_AGENT_JAR;
            f = new File(agentPath);
            if (!f.exists())
            {
                throw new IOException("Management agent not found.");
            }
        }
        agentPath = f.getCanonicalPath();
        return agentPath;
    }

    private static URLClassLoader getToolsClassLoader() throws MalformedURLException
    {
        // Classes are all dynamically loaded, since they are specific to Sun VM
        // if it fails for any reason default jmx url will be used

        // tools.jar are not always included used by default class loader, so we
        // will try to use custom loader that will try to load tools.jar

        String javaHome = System.getProperty("java.home");
        String tools = javaHome + File.separator + ".." + File.separator + "lib" + File.separator + "tools.jar";
        URLClassLoader loader = new URLClassLoader(new URL[] { new File(tools).toURI().toURL() });

        return loader;
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException
    {
        Map<String, String> attachableVMs = JmxReflectionUtil.getAttachableVMs();

        System.out.println("请输入VmPid:");
        Scanner scanner = new Scanner(System.in);
        String vmId = scanner.nextLine();
        scanner.close();

        String localJmxAddress = JmxReflectionUtil.findJMXUrlByProcessId(vmId);

        JMXServiceURL url = new JMXServiceURL(localJmxAddress);

        JMXConnector jmxc = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(mbsc, "java.lang:type=Runtime",
                RuntimeMXBean.class);

        System.out.println(rmxb.getName());
    }
}
