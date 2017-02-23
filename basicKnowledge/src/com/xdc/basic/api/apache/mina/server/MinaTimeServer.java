package com.xdc.basic.api.apache.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTimeServer
{
    // 定义监听端口
    private static final int PORT = 9123;

    public static void main(String[] args) throws IOException
    {
        // 创建服务端接收器
        IoAcceptor acceptor = new NioSocketAcceptor();

        // 指定日志过滤器 及 编码过滤器
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        // 指定业务逻辑处理器
        acceptor.setHandler(new TimeServerHandler());

        // 设置缓存大小 及 空闲时间
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        // 启动监听
        acceptor.bind(new InetSocketAddress(PORT));
    }
}