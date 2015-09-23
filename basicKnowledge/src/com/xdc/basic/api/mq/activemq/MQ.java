package com.xdc.basic.api.mq.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class MQ
{
    private static Connection connection = null;

    public static void sendQueue(String message, String queueName) throws JMSException
    {
        Connection connection = getConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = new ActiveMQQueue(queueName);

        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage(message));

        producer.close();
        session.close();
    }

    public static void sendTopic(String message, String topicName) throws JMSException
    {
        Connection connection = getConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = new ActiveMQTopic(topicName);

        MessageProducer producer = session.createProducer(topic);
        producer.send(session.createTextMessage(message));

        producer.close();
        session.close();
    }

    public static Message receiveQueue(String queueName) throws JMSException
    {
        Connection connection = getConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = new ActiveMQQueue(queueName);

        MessageConsumer comsumer = session.createConsumer(queue);
        // comsumer.getMessageSelector();
        // comsumer.getMessageListener();
        // comsumer.receive();
        Message message = comsumer.receive(5000);

        comsumer.close();
        session.close();

        return message;
    }

    public static Message receiveTopic(String topicName) throws JMSException
    {
        Connection connection = getConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = new ActiveMQTopic(topicName);

        MessageConsumer comsumer = session.createConsumer(topic);
        // comsumer.getMessageSelector();
        // comsumer.getMessageListener();
        // comsumer.receive();
        Message message = comsumer.receive(5000);

        comsumer.close();
        session.close();

        return message;
    }

    public static void registerListenerQueue(String queueName, MessageListener listener) throws JMSException
    {
        Connection connection = getConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = new ActiveMQQueue(queueName);

        MessageConsumer comsumer = session.createConsumer(queue);
        comsumer.setMessageListener(listener);

        comsumer.close();
        session.close();
    }

    public static void registerListenerTopic(String topicName, MessageListener listener) throws JMSException
    {
        Connection connection = getConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = new ActiveMQTopic(topicName);

        MessageConsumer comsumer = session.createConsumer(topic);
        comsumer.setMessageListener(listener);

        comsumer.close();
        session.close();
    }

    private static Connection getConnection() throws JMSException
    {
        if (connection == null)
        {
            synchronized (Connection.class)
            {
                if (connection == null)
                {
                    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                            "failover:tcp://192.168.224.128:61616");
                    connection = factory.createConnection();
                    connection.start();
                }
            }
        }
        return connection;
    }
}
