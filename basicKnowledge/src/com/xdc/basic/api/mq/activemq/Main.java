package com.xdc.basic.api.mq.activemq;

import javax.jms.JMSException;

public class Main
{
    public static void main(String[] args) throws JMSException
    {
        // MqNode mqNode = new MqNode("xdc-quque", NodeType.Queue);

        while (true)
        {
            // MQ.sendQueue("I am xdc.", mqNode);
            // MQ.sendTopic("I am xdc.", "xdc-topic");
        }
    }
}
