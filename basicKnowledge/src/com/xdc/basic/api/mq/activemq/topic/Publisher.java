package com.xdc.basic.api.mq.activemq.topic;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class Publisher
{
    public static void main(String[] args) throws Exception
    {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("failover:tcp://192.168.224.128:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Topic topic = new ActiveMQTopic("testTopic");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 创建一个生产者，然后发送多个消息
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 10; i++)
        {
            producer.send(session.createTextMessage("Message:" + i));
        }
    }
}