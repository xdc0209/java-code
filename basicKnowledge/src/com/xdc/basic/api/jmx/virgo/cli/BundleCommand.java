package com.xdc.basic.api.jmx.virgo.cli;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularData;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.jmx.virgo.cli.core.Option;

public class BundleCommand extends AbstractJmxCommand
{
    // 名称，可选，带参数
    private Option nameOption    = new Option("-n", false, true);

    // 版本，可选，带参数
    private Option versionOption = new Option("-v", false, true);

    // 状态，可选，带参数
    private Option stateOption   = new Option("-s", false, true);

    // 总数，可选，不带参数 
    private Option totalOption   = new Option("-t", false, false);

    @Override
    protected void initOptions()
    {
        options.put(nameOption.getOption(), nameOption);
        options.put(versionOption.getOption(), versionOption);
        options.put(stateOption.getOption(), stateOption);
        options.put(totalOption.getOption(), totalOption);
    }

    @Override
    protected void excute()
    {
        try
        {
            MBeanServerConnection mbsc = getBeanServerConnection();

            String nameArg = parsedOptions.get(nameOption.getOption());
            String versionArg = parsedOptions.get(versionOption.getOption());
            String stateArg = parsedOptions.get(stateOption.getOption());
            boolean total = parsedOptions.containsKey(totalOption.getOption());

            StringBuilder sb = new StringBuilder();

            sb.append("org.eclipse.virgo.kernel:type=ArtifactModel,artifact-type=bundle");

            sb.append(",name=*");
            if (StringUtils.isNotBlank(nameArg))
            {
                sb.append(nameArg).append("*");
            }

            sb.append(",version=*");
            if (StringUtils.isNotBlank(versionArg))
            {
                sb.append(versionArg).append("*");
            }

            sb.append(",region=*");

            ObjectName bundleObjectName = new ObjectName(sb.toString());

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

                if (StringUtils.isBlank(stateArg) || StringUtils.equalsIgnoreCase(stateArg, state.toString()))
                {
                    Bundle bundle = new Bundle(bundleId, name, version, state);
                    map.put(bundleId, bundle);
                }
            }

            if (total)
            {
                System.out.println(map.size());
            }
            else
            {
                System.out.printf("%-5s%-12s%s\n", "id", "State", "Bundle");
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
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
