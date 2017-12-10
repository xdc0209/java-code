package com.xdc.basic.tools.ip.proxy;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.xdc.basic.tools.ip.proxy.io.handler.ClientToProxyIoHandler;

/**
 * 摘自：https://www.oschina.net/code/snippet_16_2529
 * 描述：程序启动后，将监听0.0.0.0:proxy-port，然后将接收到的流量转发到server-hostname:server-port
 */
public class Main
{
    public static void main(String[] args) throws Exception
    {
        if (args.length != 3)
        {
            System.err.println(Main.class.getName() + " <proxy-port> <server-hostname> <server-port>");
            System.exit(1);
        }

        int proxyPort = Integer.parseInt(args[0]);
        String serverHostname = args[1];
        int serverPort = Integer.parseInt(args[2]);

        // Create TCP/IP acceptor.
        NioSocketAcceptor acceptor = new NioSocketAcceptor();

        // Create TCP/IP connector.
        IoConnector connector = new NioSocketConnector();

        // Set connect timeout.
        connector.setConnectTimeoutMillis(30 * 1000L);

        ClientToProxyIoHandler handler = new ClientToProxyIoHandler(connector,
                new InetSocketAddress(serverHostname, serverPort));

        // Start proxy.
        acceptor.setHandler(handler);
        acceptor.bind(new InetSocketAddress(proxyPort));

        System.out.println("Listening on port: " + proxyPort);
    }
}
