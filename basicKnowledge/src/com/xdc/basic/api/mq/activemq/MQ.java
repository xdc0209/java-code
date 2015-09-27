package com.xdc.basic.api.mq.activemq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.mq.activemq.MqNode.NodeType;

public class MQ
{
    private static Connection                                       connection = null;

    private static Session                                          session    = null;

    private static ConcurrentHashMap<MqNode, MessageProducer>       producers  = new ConcurrentHashMap<MqNode, MessageProducer>();

    private static ConcurrentHashMap<MqNode, List<MessageConsumer>> consumers  = new ConcurrentHashMap<MqNode, List<MessageConsumer>>();

    public static void send(MqNode mqNode, String message) throws JMSException
    {
        Session session = getSession();
        MessageProducer producer = getProducer(mqNode);
        producer.send(session.createTextMessage(message));
    }

    public static void registerListener(MqNode mqNode, MessageListener listener) throws JMSException
    {
        List<MessageConsumer> mqNodeConsumers = consumers.get(mqNode);
        if (mqNodeConsumers == null)
        {
            mqNodeConsumers = new ArrayList<>();
            if (consumers.putIfAbsent(mqNode, mqNodeConsumers) != null)
            {
                mqNodeConsumers = consumers.get(mqNode);
            }
        }

        MessageConsumer consumer = createConsumer(mqNode);
        mqNodeConsumers.add(consumer);

        consumer.setMessageListener(listener);
    }

    public static MessageProducer getProducer(MqNode mqNode) throws JMSException
    {
        MessageProducer producer = producers.get(mqNode);
        if (producer == null)
        {
            producer = createProducer(mqNode);
            if (producers.putIfAbsent(mqNode, producer) != null)
            {
                producer.close();
                producer = producers.get(mqNode);
            }

        }

        return producer;
    }

    public static MessageProducer createProducer(MqNode mqNode) throws JMSException
    {
        Session session = getSession();

        Destination destination = null;
        if (mqNode.getNodeType() == NodeType.Queue)
        {
            destination = session.createQueue(mqNode.getNodeKey());
        }
        else
        {
            destination = session.createTopic(mqNode.getNodeKey());
        }

        MessageProducer producer = session.createProducer(destination);
        return producer;
    }

    public static MessageConsumer createConsumer(MqNode mqNode) throws JMSException
    {
        Session session = getSession();

        Destination destination = null;
        if (mqNode.getNodeType() == NodeType.Queue)
        {
            destination = session.createQueue(mqNode.getNodeKey());
        }
        else
        {
            destination = session.createTopic(mqNode.getNodeKey());
        }

        MessageConsumer consumer = null;
        if (StringUtils.isBlank(mqNode.getSelector()))
        {
            consumer = session.createConsumer(destination);
        }
        else
        {
            consumer = session.createConsumer(destination, mqNode.getSelector());
        }
        return consumer;
    }

    private static Session getSession() throws JMSException
    {
        if (session == null)
        {
            synchronized (Session.class)
            {
                if (session == null)
                {
                    session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
                }
            }
        }
        return session;
    }

    private static Connection getConnection() throws JMSException
    {
        if (connection == null)
        {
            synchronized (Connection.class)
            {
                if (connection == null)
                {
                    String brokerURL = "failover:(tcp://192.168.224.128:61616)?nested.wireFormat.maxInactivityDuration=0";
                    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
                    connection = factory.createConnection();
                    connection.start();
                }
            }
        }
        return connection;
    }
}
