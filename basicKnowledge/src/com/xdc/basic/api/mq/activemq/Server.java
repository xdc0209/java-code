package com.xdc.basic.api.mq.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Server implements MessageListener
{
    @Override
    public void onMessage(Message requst)
    {
        try
        {
            Session session = MQ.getSession();
            TextMessage response = session.createTextMessage("xdc ans");

            MessageProducer producer = MQ.createProducer(new MqNode(null, MqNode.NodeType.Queue));

            response.setJMSCorrelationID(requst.getJMSCorrelationID());

            producer.send(requst.getJMSReplyTo(), response);
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
}
