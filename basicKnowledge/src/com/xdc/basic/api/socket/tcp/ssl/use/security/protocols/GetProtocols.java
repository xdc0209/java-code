package com.xdc.basic.api.socket.tcp.ssl.use.security.protocols;

import java.util.Arrays;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class GetProtocols
{
    public static void main(String[] args)
    {
        try
        {
            String javaVersion = System.getProperty("java.version");
            System.out.println("JAVA: " + javaVersion);

            SSLServerSocketFactory sslServersocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
                    .getDefault();
            SSLServerSocket sslServersocket = (SSLServerSocket) sslServersocketfactory.createServerSocket(9999);

            System.out.println();
            System.out.println("SupportedProtocols:");
            String[] supportedProtocols = sslServersocket.getSupportedProtocols();
            Arrays.sort(supportedProtocols);
            for (String supportedProtocol : supportedProtocols)
            {
                System.out.println(supportedProtocol);
            }

            System.out.println();
            System.out.println("EnabledProtocols:");
            String[] enabledProtocols = sslServersocket.getEnabledProtocols();
            Arrays.sort(enabledProtocols);
            for (String enabledProtocol : enabledProtocols)
            {
                System.out.println(enabledProtocol);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}