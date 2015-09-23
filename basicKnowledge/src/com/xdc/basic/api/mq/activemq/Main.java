package com.xdc.basic.api.mq.activemq;

import javax.jms.JMSException;

public class Main
{
    public static void main(String[] args) throws JMSException
    {
        while (true)
        {
            MQ.sendQueue("I am xdc.", "xdc-queue");
            MQ.sendTopic("I am xdc.", "xdc-topic");
        }
    }
}
