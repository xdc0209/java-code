package com.xdc.basic.api.apache.mina.server;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter
{
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception
    {
        cause.printStackTrace();
    }

    @Override
    public void sessionCreated(IoSession session)
    {
        // 显示客户端的ip和端口
        System.out.println("Client address: " + session.getRemoteAddress().toString());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        String rcvMsg = message.toString();
        if (rcvMsg.trim().equalsIgnoreCase("quit"))
        {
            // 结束会话
            session.close(true);
            return;
        }

        System.out.println("Receive message: " + rcvMsg);

        String sendMsg = "Server time: " + new Date().toString();
        System.out.println("Send message: " + sendMsg);
        System.out.println();

        // 返回当前时间的字符串
        session.write(sendMsg);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception
    {
        System.out.println("IDLE " + session.getIdleCount(status));
    }
}