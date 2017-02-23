package com.xdc.basic.api.jmx.virgo.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.jmx.virgo.cli.core.Option;

public class PlanCommand extends AbstractJmxCommand
{
    // 名称，可选，带参数
    private Option nameOption    = new Option("-n", false, true, "query plan with name");

    // 版本，可选，带参数
    private Option versionOption = new Option("-v", false, true, "query plan with version");

    // 状态，可选，带参数
    private Option stateOption   = new Option("-s", false, true, "query plan with state");

    // 总数，可选，不带参数
    private Option totalOption   = new Option("-t", false, false, "total of result plans");

    @Override
    protected void initOptions(Map<String, Option> options)
    {
        options.put(nameOption.getOption(), nameOption);
        options.put(versionOption.getOption(), versionOption);
        options.put(stateOption.getOption(), stateOption);
        options.put(totalOption.getOption(), totalOption);
    }

    @Override
    protected void excute(Map<String, String> parsedOptions, List<String> parsedArguments)
    {
        try
        {
            MBeanServerConnection mbsc = getBeanServerConnection();

            String nameArg = parsedOptions.get(nameOption.getOption());
            String versionArg = parsedOptions.get(versionOption.getOption());
            String stateArg = parsedOptions.get(stateOption.getOption());
            boolean total = parsedOptions.containsKey(totalOption.getOption());

            StringBuilder sb = new StringBuilder();

            sb.append("org.eclipse.virgo.kernel:type=ArtifactModel,artifact-type=plan");

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

            ObjectName planObjectName = new ObjectName(sb.toString());

            List<Plan> list = new ArrayList<Plan>();

            Set<ObjectName> queryNames = mbsc.queryNames(planObjectName, null);
            for (ObjectName objectName : queryNames)
            {
                Object state = mbsc.getAttribute(objectName, "State");
                if (StringUtils.isBlank(stateArg) || StringUtils.equalsIgnoreCase(stateArg, state.toString()))
                {
                    Object name = mbsc.getAttribute(objectName, "Name");
                    Object version = mbsc.getAttribute(objectName, "Version");

                    Plan plan = new Plan(name, version, state);
                    list.add(plan);
                }
            }

            if (total)
            {
                System.out.println(list.size());
            }
            else
            {
                System.out.printf("%-12s%s\n", "State", "Plan");
                for (Plan plan : list)
                {
                    Object name = plan.getName();
                    Object version = plan.getVersion();
                    Object state = plan.getState();

                    System.out.printf("%-12s%s_%s\n", state, name, version);
                }
                System.out.println();
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

class Plan
{
    private final Object name;
    private final Object version;
    private final Object state;

    public Plan(Object name, Object version, Object state)
    {
        super();
        this.name = name;
        this.version = version;
        this.state = state;
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
