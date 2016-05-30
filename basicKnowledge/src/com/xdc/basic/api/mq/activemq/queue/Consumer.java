package com.xdc.basic.api.mq.activemq.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Consumer
{
    public static void main(String[] args) throws Exception
    {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("failover:tcp://192.168.224.128:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 注册消费者1
        MessageConsumer comsumer1 = session.createConsumer(queue);
        comsumer1.setMessageListener(new MessageListener()
        {
            @Override
            public void onMessage(Message m)
            {
                try
                {
                    System.out.println("Consumer1 get " + ((TextMessage) m).getText());
                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                }
            }
        });

        // 注册消费者2
        MessageConsumer comsumer2 = session.createConsumer(queue);
        comsumer2.setMessageListener(new MessageListener()
        {
            @Override
            public void onMessage(Message m)
            {
                try
                {
                    System.out.println("Consumer2 get " + ((TextMessage) m).getText());
                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
