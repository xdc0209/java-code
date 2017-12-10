package com.xdc.basic.api.apache.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter
{
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception
    {
        // 显示接收到的消息
        System.out.println("Receive message: " + message);
        System.out.println();
    }
}
