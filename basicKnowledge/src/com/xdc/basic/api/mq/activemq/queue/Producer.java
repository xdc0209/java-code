package com.xdc.basic.api.mq.activemq.queue;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Producer
{
    public static void main(String[] args) throws Exception
    {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("failover:tcp://192.168.224.128:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 创建一个生产者，然后发送多个消息
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 10; i++)
        {
            producer.send(session.createTextMessage("Message:" + i));
        }
    }
}