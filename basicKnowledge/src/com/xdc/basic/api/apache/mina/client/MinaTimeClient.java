package com.xdc.basic.api.apache.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient
{
    public static void main(String[] args)
    {
        // 创建客户端连接器
        NioSocketConnector connector = new NioSocketConnector();

        // 指定日志过滤器 及 编码过滤器 
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        connector.setConnectTimeoutMillis(30 * 1000L);

        // 设置事件处理器 
        connector.setHandler(new TimeClientHandler());

        // 建立连接 
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 9123));

        //等待连接创建完成 
        cf.awaitUninterruptibly();

        //发送消息 
        cf.getSession().write("hello");
        cf.getSession().write("quit");

        // 等待连接断开 
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}