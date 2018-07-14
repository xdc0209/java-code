package com.xdc.basic.api.jmx.virgo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Virgo
{
    // 需要引入jmxremote_optional.jar以支持jmxmp协议
    // java.net.MalformedURLException: Unsupported protocol: jmxmp

    // 为了安全，virgo的jmx端口开在127.0.0.1:19875上，这时如果想在其他机器上连接virgo的jmx服务，可通过端口转发实现。

    // 启动端口转发(在virgo所在的服务器执行)
    // linux:~ # ssh -NTf -L 0.0.0.0:9875:127.0.0.1:19875 root@127.0.0.1

    // 关闭端口转发
    // linux:~ # PID=$(ps -eo pid,args | grep "ssh -NTf -L 0.0.0.0:9875:127.0.0.1:19875 root@127.0.0.1" | grep -v grep | cut -c1-6)
    // linux:~ # [ -n "$PID" ] && echo "kill -9 $PID" && kill -9 $PID && unset PID

    public static void main(String[] args) throws IOException, InstanceNotFoundException, MBeanException,
            ReflectionException, MalformedObjectNameException, IntrospectionException, AttributeNotFoundException
    {
        // virgo的服务器可能使用rmi协议，也可能使用jmxmp协议，具体是那种你的virgo怎么配置的，格式样例分别如下：
        // rmi: service:jmx:rmi:///jndi/rmi://Virgo_Server_IP_Address:9875/jmxrmi
        // jmxmp: service:jmx:jmxmp://Virgo_Server_IP_Address:9875

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.1.100:9875/jmxrmi");

        Map<String, Object> env = new HashMap<String, Object>();
        env.put(JMXConnector.CREDENTIALS, new String[] { "admin", "springsource" });

        JMXConnector jmxc = JMXConnectorFactory.connect(url, env);

        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        printPlanState(mbsc);

        printBundleState(mbsc);
    }

    private static void printPlanState(MBeanServerConnection mbsc) throws IOException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException, MalformedObjectNameException
    {
        System.out.printf("%-12s%s\n", "State", "Plan");

        ObjectName planObjectName = new ObjectName(
                "org.eclipse.virgo.kernel:type=ArtifactModel,artifact-type=plan,name=*,version=*,region=*");

        Set<ObjectName> queryNames = mbsc.queryNames(planObjectName, null);
        for (ObjectName objectName : queryNames)
        {
            Object name = mbsc.getAttribute(objectName, "Name");
            Object version = mbsc.getAttribute(objectName, "Version");
            Object state = mbsc.getAttribute(objectName, "State");

            System.out.printf("%-12s%s_%s\n", state, name, version);
        }
        System.out.println();
    }

    private static void printBundleState(MBeanServerConnection mbsc) throws MalformedObjectNameException,
            InstanceNotFoundException, MBeanException, ReflectionException, IOException, AttributeNotFoundException
    {
        System.out.printf("%-5s%-12s%s\n", "id", "State", "Bundle");

        ObjectName bundleObjectName = new ObjectName(
                "org.eclipse.virgo.kernel:type=ArtifactModel,artifact-type=bundle,name=*,version=*,region=*");

        // 使用treeMap保存，结果按自动按bundleId排序
        Map<Integer, Bundle> map = new TreeMap<Integer, Bundle>();

        Set<ObjectName> queryNames = mbsc.queryNames(bundleObjectName, null);
        for (ObjectName objectName : queryNames)
        {
            // 由于bundle一般较多，批量查询需要的属性，提高性能
            String[] attributeArr = new String[] { "Name", "Version", "State", "Properties" };
            AttributeList attributes = mbsc.getAttributes(objectName, attributeArr);

            Object name = ((Attribute) (attributes.get(0))).getValue();
            Object version = ((Attribute) (attributes.get(1))).getValue();
            Object state = ((Attribute) (attributes.get(2))).getValue();

            Object properties = ((Attribute) (attributes.get(3))).getValue();
            Integer bundleId = parseBundleId(properties);

            Bundle bundle = new Bundle(bundleId, name, version, state);
            map.put(bundleId, bundle);
        }

        for (Bundle bundle : map.values())
        {
            Integer bundleId = bundle.getBundleId();
            Object name = bundle.getName();
            Object version = bundle.getVersion();
            Object state = bundle.getState();

            System.out.printf("%-5s%-12s%s_%s\n", bundleId, state, name, version);
        }

        System.out.println();
    }

    /**
     * 从复杂的表结构中解析出bunleId
     */
    private static Integer parseBundleId(Object properties)
    {
        TabularData tabularData = (TabularData) properties;
        CompositeDataSupport compositeData = (CompositeDataSupport) tabularData.get(new String[] { "Bundle Id" });
        Object bundleId = compositeData.get("value");
        return Integer.valueOf(String.valueOf(bundleId));
    }

    // /**
    // * 通过osgi.core中的listBundles方法获取bundle状态，但是不同版virgo可能没有此域，兼容性不好，不用此方法。
    // */
    // private static void printBundleStatef(MBeanServerConnection mbsc) throws MalformedObjectNameException,
    // InstanceNotFoundException, MBeanException, ReflectionException, IOException
    // {
    //
    // ObjectName kernelObjectName = new ObjectName(
    // "osgi.core:version=1.5,type=bundleState,region=org.eclipse.equinox.region.kernel");
    // ObjectName userObjectName = new ObjectName(
    // "osgi.core:version=1.5,type=bundleState,region=org.eclipse.virgo.region.user");
    //
    // Object listKernelBundlesResult = mbsc.invoke(kernelObjectName, "listBundles", null, null);
    // Object listUserBundlesResult = mbsc.invoke(userObjectName, "listBundles", null, null);
    //
    // if (listKernelBundlesResult instanceof TabularData && listUserBundlesResult instanceof TabularData)
    // {
    // TabularData kernelTabularData = (TabularData) listKernelBundlesResult;
    // TabularData userTabularData = (TabularData) listUserBundlesResult;
    //
    // System.out.println("==== Kernel Bundles State ====");
    // System.out.printf("%-5s%-12s%s\n", "id", "State", "Bundle");
    // for (Object o : kernelTabularData.values())
    // {
    // if (o instanceof CompositeData)
    // {
    // CompositeData compositeData = (CompositeData) o;
    //
    // Object identifier = compositeData.get("Identifier");
    // Object state = compositeData.get("State");
    // Object symbolicName = compositeData.get("SymbolicName");
    // Object version = compositeData.get("Version");
    // System.out.printf("%-5s%-12s%s_%s\n", identifier, state, symbolicName, version);
    // }
    // }
    // System.out.println();
    //
    // System.out.println("==== User Bundles State ====");
    // System.out.printf("%-5s%-12s%s\n", "id", "State", "Bundle");
    // for (Object o : userTabularData.values())
    // {
    // if (o instanceof CompositeData)
    // {
    // CompositeData compositeData = (CompositeData) o;
    //
    // Object identifier = compositeData.get("Identifier");
    // Object state = compositeData.get("State");
    // Object symbolicName = compositeData.get("SymbolicName");
    // Object version = compositeData.get("Version");
    // System.out.printf("%-5s%-12s%s_%s\n", identifier, state, symbolicName, version);
    // }
    // }
    // System.out.println();
    // }
    // }
}

class Bundle
{
    private final Integer bundleId;
    private final Object  name;
    private final Object  version;
    private final Object  state;

    public Bundle(Integer bundleId, Object name, Object version, Object state)
    {
        super();
        this.bundleId = bundleId;
        this.name = name;
        this.version = version;
        this.state = state;
    }

    public Integer getBundleId()
    {
        return bundleId;
    }

    public Object getName()
    {
        return name;
    }

    public Object getVersion()
    {
        return version;
    }

    public Object getState()
    {
        return state;
    }
}
