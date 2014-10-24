package com.xdc.basic.api.jmx.example.basic;

import java.io.IOException;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.JMX;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Client
{
    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException, MalformedObjectNameException,
            InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException,
            ReflectionException, MBeanException, InstanceNotFoundException, AttributeNotFoundException,
            InvalidAttributeValueException, ListenerNotFoundException, InterruptedException
    {
        // 建立rmi连接客户端并连接rmi连接服务器
        // Create an RMI connector client and connect it to the RMI connector server
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/myserver");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

        // Create listener
        ClientListener listener = new ClientListener();

        // Get an MBeanServerConnection
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        // Get MBeanServer's default domain
        String defaultDomain = mbsc.getDefaultDomain();

        // Create SimpleStandard MBean
        String stdMBeanClassName = SimpleStandard.class.getCanonicalName();
        ObjectName stdMBeanName = new ObjectName(String.format("%s:type=%s,name=2", defaultDomain, stdMBeanClassName));
        mbsc.createMBean(stdMBeanClassName, stdMBeanName, null, null);

        // Create SimpleDynamic MBean
        String dynMBbeanClassName = SimpleDynamic.class.getCanonicalName();
        ObjectName dynMBeanName = new ObjectName(String.format("%s:type=%s,name=2", defaultDomain, dynMBbeanClassName));
        mbsc.createMBean(dynMBbeanClassName, dynMBeanName, null, null);

        // Get MBean count
        echo("MBean count = " + mbsc.getMBeanCount());

        // Query MBeans
        Set<ObjectInstance> queryMBeans = mbsc.queryMBeans(null, null);
        // Query MBean names
        Set<ObjectName> names = mbsc.queryNames(null, null);

        // 管理标准MBean ==================================================
        // Get State attribute in SimpleStandard MBean
        echo("State = " + mbsc.getAttribute(stdMBeanName, "State"));

        // Set State attribute in SimpleStandard MBean
        mbsc.setAttribute(stdMBeanName, new Attribute("State", "Changed State"));

        // Get State attribute in SimpleStandard MBean
        // 与一个给定的MBean互动的另一种方法是通过一个专门的代理, 而不是直接通过MBean服务器连接
        // Another way of interacting with a given MBean is through a dedicated proxy instead of going directly through the MBean server connection
        SimpleStandardMBean proxy = JMX.newMBeanProxy(mbsc, stdMBeanName, SimpleStandardMBean.class, true);
        echo("State = " + proxy.getState());

        // Add notification listener on SimpleStandard MBean
        mbsc.addNotificationListener(stdMBeanName, listener, null, null);

        // Invoke "reset" in SimpleStandard MBean
        // Calling "reset" makes the SimpleStandard MBean emit a notification that will be received by the registered ClientListener.
        mbsc.invoke(stdMBeanName, "reset", null, null);

        // Sleep for 2 seconds in order to have time to receive the notification before removing the notification listener.
        echo("Waiting for notification...");
        Thread.sleep(2000);

        // Remove notification listener on SimpleStandard MBean
        mbsc.removeNotificationListener(stdMBeanName, listener);

        // Unregister SimpleStandard MBean
        mbsc.unregisterMBean(stdMBeanName);

        // 管理动态MBean ==================================================
        // Get State attribute in SimpleDynamic MBean
        echo("State = " + mbsc.getAttribute(dynMBeanName, "State"));

        // Set State attribute in SimpleDynamic MBean
        mbsc.setAttribute(dynMBeanName, new Attribute("State", "Changed State"));

        // Get State attribute in SimpleDynamic MBean
        echo("State = " + mbsc.getAttribute(dynMBeanName, "State"));

        // Add notification listener on SimpleDynamic MBean
        mbsc.addNotificationListener(dynMBeanName, listener, null, null);

        // Invoke "reset" in SimpleDynamic MBean
        // Calling "reset" makes the SimpleDynamic MBean emit a notification that will be received by the registered ClientListener.
        mbsc.invoke(dynMBeanName, "reset", null, null);

        // Sleep for 2 seconds in order to have time to receive the notification before removing the notification listener.
        echo("Waiting for notification...");
        Thread.sleep(2000);

        // Remove notification listener on SimpleDynamic MBean
        mbsc.removeNotificationListener(dynMBeanName, listener);

        // Unregister SimpleDynamic MBean
        mbsc.unregisterMBean(dynMBeanName);

        // Close MBeanServer connection
        jmxc.close();
    }

    private static void echo(String msg)
    {
        System.out.println(msg);
    }
}
