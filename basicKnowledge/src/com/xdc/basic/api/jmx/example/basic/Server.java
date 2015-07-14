package com.xdc.basic.api.jmx.example.basic;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Server
{
    public static void main(String[] args) throws InstanceAlreadyExistsException, NotCompliantMBeanException,
            MBeanRegistrationException, MBeanException, ReflectionException, IntrospectionException,
            InstanceNotFoundException, MalformedObjectNameException, InvalidAttributeValueException,
            AttributeNotFoundException, IOException
    {
        // 获得MBeanServer实例  ==================================================
        // MBeanServer mbs = MBeanServerFactory.createMBeanServer(); // 不能在jconsole中使用  
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); // 可在jconsole中使用  

        // Get default domain
        String defaultDomain = mbs.getDefaultDomain();

        // 标准MBean样例 =========================================================
        // Create and register the SimpleStandard MBean
        String mbeanClassName = SimpleStandard.class.getCanonicalName();
        ObjectName mbeanObjectName = new ObjectName(String.format("%s:type=%s,name=1", defaultDomain, mbeanClassName));
        createSimpleMBean(mbs, mbeanClassName, mbeanObjectName);

        // Get and display the management information exposed by the SimpleStandard MBean
        MBeanInfo mbeanInfo = mbs.getMBeanInfo(mbeanObjectName);
        printMBeanInfo(mbeanInfo);

        // Manage the SimpleStandard MBean
        manageSimpleMBean(mbs, mbeanObjectName, mbeanClassName);

        // 动态MBean样例 =========================================================
        // Create and register the SimpleDynamic MBean
        mbeanClassName = SimpleDynamic.class.getCanonicalName();
        mbeanObjectName = new ObjectName(String.format("%s:type=%s,name=1", defaultDomain, mbeanClassName));
        createSimpleMBean(mbs, mbeanClassName, mbeanObjectName);

        // Get and display the management information exposed by the SimpleDynamic MBean
        mbeanInfo = mbs.getMBeanInfo(mbeanObjectName);
        printMBeanInfo(mbeanInfo);

        // Manage the SimpleDynamic MBean
        manageSimpleMBean(mbs, mbeanObjectName, mbeanClassName);

        // 建立rmi连接服务器
        // Create an RMI connector server
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/myserver");
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        LocateRegistry.createRegistry(9999);

        // jmxmp协议的服务端样例
        // JMXServiceURL url = new JMXServiceURL("jmxmp", "localhost", 9999);
        // Map<String, Object> envMap = new HashMap<String, Object>();
        // envMap.put("jmx.remote.server.address.wildcard", "false");
        // JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, envMap, mbs);

        // Start the RMI connector server
        echo("Start the RMI connector server");
        cs.start();
        echo("The RMI connector server successfully started and is ready to handle incoming connections.");
        echo("Start the client on a different window and press <Enter> once the client has finished");

        echo("Press <Enter> to continue...");
        System.in.read();

        // Stop the RMI connector server
        echo("Stop the RMI connector server");
        cs.stop();
    }

    private static void createSimpleMBean(MBeanServer mbs, String mbeanClassName, ObjectName mbeanObjectName)
            throws InstanceAlreadyExistsException, NotCompliantMBeanException, MBeanRegistrationException,
            MBeanException, ReflectionException
    {
        mbs.createMBean(mbeanClassName, mbeanObjectName);
    }

    private static void printMBeanInfo(MBeanInfo mbeanInfo) throws IntrospectionException, InstanceNotFoundException,
            ReflectionException
    {
        echo("================ Print MBean Info Start ================");
        echo("CLASSNAME:   " + mbeanInfo.getClassName());
        echo("DESCRIPTION: " + mbeanInfo.getDescription());

        echo("ATTRIBUTES");
        MBeanAttributeInfo[] attrInfo = mbeanInfo.getAttributes();
        if (attrInfo.length > 0)
        {
            for (int i = 0; i < attrInfo.length; i++)
            {
                echo(" ** NAME:  " + attrInfo[i].getName());
                echo("    DESCR: " + attrInfo[i].getDescription());
                echo("    TYPE:  " + attrInfo[i].getType());
                echo("    READ:  " + attrInfo[i].isReadable());
                echo("    WRITE: " + attrInfo[i].isWritable());
            }
        }
        else
        {
            echo(" ** No attributes **");
        }

        echo("CONSTRUCTORS");
        MBeanConstructorInfo[] constrInfo = mbeanInfo.getConstructors();
        for (int i = 0; i < constrInfo.length; i++)
        {
            echo(" ** NAME:  " + constrInfo[i].getName());
            echo("    DESCR: " + constrInfo[i].getDescription());
            echo("    PARAM: " + constrInfo[i].getSignature().length + " parameter(s)");
        }

        echo("OPERATIONS");
        MBeanOperationInfo[] opInfo = mbeanInfo.getOperations();
        if (opInfo.length > 0)
        {
            for (int i = 0; i < opInfo.length; i++)
            {
                echo(" ** NAME:  " + opInfo[i].getName());
                echo("    DESCR: " + opInfo[i].getDescription());
                echo("    PARAM: " + opInfo[i].getSignature().length + " parameter(s)");
            }
        }
        else
        {
            echo(" ** No operations ** ");
        }

        echo("NOTIFICATIONS");
        MBeanNotificationInfo[] notifInfo = mbeanInfo.getNotifications();
        if (notifInfo.length > 0)
        {
            for (int i = 0; i < notifInfo.length; i++)
            {
                echo(" ** NAME:  " + notifInfo[i].getName());
                echo("    DESCR: " + notifInfo[i].getDescription());
                String notifTypes[] = notifInfo[i].getNotifTypes();
                echo("    TYPE:");
                for (int j = 0; j < notifTypes.length; j++)
                {
                    echo("        " + notifTypes[j]);
                }
            }
        }
        else
        {
            echo(" ** No notifications **");
        }

        echo("================ Print MBean Info End ================");
    }

    private static void manageSimpleMBean(MBeanServer mbs, ObjectName mbeanObjectName, String mbeanClassName)
            throws InstanceNotFoundException, InvalidAttributeValueException, AttributeNotFoundException,
            ReflectionException, MBeanException
    {
        // Get attribute values
        printSimpleAttributes(mbs, mbeanObjectName);

        // Change State attribute
        Attribute stateAttribute = new Attribute("State", "New State");
        mbs.setAttribute(mbeanObjectName, stateAttribute);

        // Get attribute values
        printSimpleAttributes(mbs, mbeanObjectName);

        // Invoking reset operation
        mbs.invoke(mbeanObjectName, "reset", null, null);

        // Get attribute values
        printSimpleAttributes(mbs, mbeanObjectName);

    }

    private static void printSimpleAttributes(MBeanServer mbs, ObjectName mbeanObjectName)
            throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException
    {
        echo("Getting attribute values:");
        String State = (String) mbs.getAttribute(mbeanObjectName, "State");
        Integer NbChanges = (Integer) mbs.getAttribute(mbeanObjectName, "NbChanges");
        echo("    State     = " + State);
        echo("    NbChanges = " + NbChanges);
    }

    private static void echo(String msg)
    {
        System.out.println(msg);
    }
}
