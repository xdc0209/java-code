package com.xdc.basic.api.rmi.sbus.transport.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.rmi.sbus.server.SbusServer;
import com.xdc.basic.api.rmi.sbus.transport.MessageClient;

public class SocketClient implements MessageClient
{
    private static Logger log = LoggerFactory.getLogger(SbusServer.class);

    public String         ip;
    public int            port;

    public SocketClient(String ip, int port)
    {
        super();
        this.ip = ip;
        this.port = port;
    }

    public byte[] send(byte[] request) throws Exception
    {
        try
        {
            Socket socket = new Socket(ip, port);

            // 发出请求
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(request);
            outputStream.flush();

            // 获取响应
            InputStream inputStream = socket.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int length = 0;

            do
            {
                length = inputStream.read(buffer, 0, buffer.length);
                byteArrayOutputStream.write(buffer, 0, length);
            } while (length == buffer.length);
            byte[] response = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();

            inputStream.close();
            outputStream.close();
            socket.close();

            return response;
        }
        catch (Exception e)
        {
            log.error("Send message failed.", e);
            throw e;
        }

    }
}
