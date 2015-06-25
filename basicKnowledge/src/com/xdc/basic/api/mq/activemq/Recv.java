package com.xdc.basic.api.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Recv
{
    public static void main(String[] args)
    {
        // ConnectionFactory: 连接工厂, JMS用它创建连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.230.129:61616");

        // Connection: JMS客户端到JMS Provider的连接
        Connection connection = null;
        try
        {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();

            // 启动
            connection.start();

            // Session: 一个发送或接收消息的线程
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            // Destination: 消息的目的地
            Destination destination = session.createQueue("FirstQueue");

            // 消费者，消息接收者
            MessageConsumer consumer = session.createConsumer(destination);
            while (true)
            {
                //设置接收者接收消息的时间，为了便于测试，这里谁定为100s
                TextMessage message = (TextMessage) consumer.receive(100000);
                if (null == message)
                {
                    break;
                }
                System.out.println("收到消息" + message.getText());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != connection)
                {
                    connection.close();
                }
            }
            catch (Throwable ignore)
            {
            }
        }
    }
}