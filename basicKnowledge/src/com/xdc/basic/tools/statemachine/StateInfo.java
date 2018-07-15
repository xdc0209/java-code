package com.xdc.basic.tools.statemachine;

/**
 * 状态机的状态信息 .
 */
public class StateInfo
{
    // XML描述的状态名
    private String stateName;

    // 定制的用于记录当前信息的类
    private Object context;

    public StateInfo()
    {
    }

    public StateInfo(String stateName, Object context)
    {
        this.stateName = stateName;
        this.context = context;
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }

    public Object getContext()
    {
        return context;
    }

    public void setContext(Object context)
    {
        this.context = context;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("StateInfo [stateName=");
        builder.append(stateName);
        builder.append(", context=");
        builder.append(context);
        builder.append("]");
        return builder.toString();
    }
}
