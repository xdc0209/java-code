package com.xdc.basic.api.rmi.sbus.transport.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.rmi.sbus.transport.MessageListener;
import com.xdc.basic.api.rmi.sbus.transport.MessageServer;
import com.xdc.basic.api.thread.executor.threadfactory.NamePrefixThreadFactory;

public class SocketServer implements MessageServer
{
    private static Logger           log      = LoggerFactory.getLogger(SocketServer.class);

    private static volatile boolean stop     = false;

    private MessageListener         listener = null;

    private String                  ip;

    private int                     port;

    public SocketServer(String ip, int port)
    {
        super();
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void start()
    {
        ExecutorService exec = Executors.newCachedThreadPool(new NamePrefixThreadFactory("Socket-Server"));
        exec.submit(new Runnable()
        {
            @Override
            public void run()
            {
                ServerSocket serverSocket = null;
                try
                {
                    serverSocket = new ServerSocket();
                    serverSocket.bind(new InetSocketAddress(ip, port));
                    log.info("Start listener on [ {}:{} ].", ip, port);

                    ExecutorService exec = Executors.newFixedThreadPool(10);
                    while (!stop)
                    {
                        final Socket socket = serverSocket.accept();
                        log.info("Accept and handle socket [ {}:{} ].", socket.getInetAddress().getHostAddress(),
                                socket.getPort());

                        exec.submit(new Runnable()
                        {
                            public void run()
                            {
                                if (listener == null)
                                {
                                    log.info("SocketServer.listener is null. Skip to Handle message.");
                                    return;
                                }

                                InputStream inputStream = null;
                                OutputStream outputStream = null;
                                try
                                {
                                    // 接收请求
                                    inputStream = socket.getInputStream();
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    byte[] buffer = new byte[4096];
                                    int length = 0;
                                    do
                                    {
                                        length = inputStream.read(buffer, 0, buffer.length);
                                        byteArrayOutputStream.write(buffer, 0, length);
                                    } while (length == buffer.length);
                                    byte[] request = byteArrayOutputStream.toByteArray();
                                    byteArrayOutputStream.close();

                                    // 处理请求
                                    byte[] response = listener.receive(request);

                                    // 发出响应
                                    outputStream = socket.getOutputStream();
                                    outputStream.write(response);
                                    outputStream.flush();
                                }
                                catch (Throwable e)
                                {
                                    String serverIp = socket.getLocalAddress().getHostAddress();
                                    int serverPort = socket.getLocalPort();
                                    String clientIp = socket.getInetAddress().getHostAddress();
                                    int clientPort = socket.getPort();

                                    log.error(String.format(
                                            "Handle message failed. Socket info is [ serverIp=%s, serverPort=%s, clientIp=%s, clientPort=%s ].",
                                            serverIp, serverPort, clientIp, clientPort), e);
                                }
                                finally
                                {
                                    // 关闭流
                                    IOUtils.closeQuietly(inputStream);
                                    IOUtils.closeQuietly(outputStream);

                                    // 关闭socket
                                    IOUtils.closeQuietly(socket);
                                }
                            }
                        });
                    }
                }
                catch (Throwable e)
                {
                    log.error("Error occur on socker server.", e);
                }
                finally
                {
                    IOUtils.closeQuietly(serverSocket);
                }
            }
        });
    }

    @Override
    public void stop()
    {
        SocketServer.stop = true;
    }

    @Override
    public void registerListener(MessageListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void unregisterListener(MessageListener listener)
    {
        this.listener = null;
    }
}
