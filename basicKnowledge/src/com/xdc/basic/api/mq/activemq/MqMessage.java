package com.xdc.basic.api.mq.activemq;

import java.io.Serializable;

public class MqMessage implements Serializable
{
    private static final long serialVersionUID = 7858064226498287240L;

    // BytesMessage
    // ObjectMessage
    // TextMessage

    private Object            payload;

    private Object            type;

    private MqNode            replyTo;

    public Object getPayload()
    {
        return payload;
    }

    public void setPayload(Object payload)
    {
        this.payload = payload;
    }

    public Object getType()
    {
        return type;
    }

    public void setType(Object type)
    {
        this.type = type;
    }

    public MqNode getReplyTo()
    {
        return replyTo;
    }

    public void setReplyTo(MqNode replyTo)
    {
        this.replyTo = replyTo;
    }

}
