package com.xdc.basic.api.mq.activemq;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MqNode
{
    public enum NodeType
    {
        Queue, Topic;
    }

    private String   nodeKey;
    private NodeType nodeType;
    private String   selector;

    public MqNode(String nodeKey, NodeType nodeType)
    {
        super();
        this.nodeKey = nodeKey;
        this.nodeType = nodeType;
    }

    public MqNode(String nodeKey, NodeType nodeType, String selector)
    {
        super();
        this.nodeKey = nodeKey;
        this.nodeType = nodeType;
        this.selector = selector;
    }

    public String getNodeKey()
    {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey)
    {
        this.nodeKey = nodeKey;
    }

    public NodeType getNodeType()
    {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType)
    {
        this.nodeType = nodeType;
    }

    public String getSelector()
    {
        return selector;
    }

    public void setSelector(String selector)
    {
        this.selector = selector;
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
