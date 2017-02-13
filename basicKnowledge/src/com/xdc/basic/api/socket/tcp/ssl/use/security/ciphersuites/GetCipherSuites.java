package com.xdc.basic.api.socket.tcp.ssl.use.security.ciphersuites;

import java.util.Arrays;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class GetCipherSuites
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
            System.out.println("SupportedCipherSuites:");
            String[] supportedCipherSuites = sslServersocket.getSupportedCipherSuites();
            Arrays.sort(supportedCipherSuites);
            for (String supportedCipherSuite : supportedCipherSuites)
            {
                System.out.println(supportedCipherSuite);
            }

            System.out.println();
            System.out.println("EnabledCipherSuites:");
            String[] enabledCipherSuites = sslServersocket.getEnabledCipherSuites();
            Arrays.sort(enabledCipherSuites);
            for (String enabledCipherSuite : enabledCipherSuites)
            {
                System.out.println(enabledCipherSuite);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
