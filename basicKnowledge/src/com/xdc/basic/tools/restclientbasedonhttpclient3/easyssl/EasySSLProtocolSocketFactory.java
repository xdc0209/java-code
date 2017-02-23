package com.xdc.basic.tools.restclientbasedonhttpclient3.easyssl;

/*
 * ********************************************************************** **
 ** **
 ** Copyright 2002-2005 The Apache Software Foundation **
 ** **
 ** Licensed under the Apache License, Version 2.0 (the "License"); **
 ** you may not use this file except in compliance with the License. **
 ** You may obtain a copy of the License at **
 ** **
 ** http://www.apache.org/licenses/LICENSE-2.0 **
 ** **
 ** Unless required by applicable law or agreed to in writing, software **
 ** distributed under the License is distributed on an "AS IS" BASIS, **
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND either express or implied. **
 ** See the License for the specific language governing permissions and **
 ** limitations under the License. **
 ** **
 ** This software consists of voluntary contributions made by many **
 ** individuals on behalf of the Apache Software Foundation. For more **
 ** information on the Apache Software Foundation, please see **
 ** <http://www.apache.org/>. **
 ** **********************************************************************
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

/**
 * EasySSLProtocolSocketFactory can be used to creats SSL {@link Socket}s that accept self-signed certificates.
 * 
 * @author <a href="mailto:oleg -at- ural.ru">Oleg Kalnichevski </a>
 * @version 1.1
 */
public class EasySSLProtocolSocketFactory implements ProtocolSocketFactory
{
    private SSLContext sslcontext = null;

    /**
     * Constructor for EasySSLProtocolSocketFactory.
     */
    public EasySSLProtocolSocketFactory()
    {
        super();
    }

    /**
     * Create the SSL Context.
     * 
     * @return The SSLContext
     */
    private static SSLContext createEasySSLContext()
    {
        try
        {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, new TrustManager[] { new EasyX509TrustManager(null) }, null);
            return context;
        }
        catch (Exception e)
        {
            throw new HttpClientError(e.toString());
        }
    }

    /**
     * @see ProtocolSocketFactory#createSocket(java.lang.String,int)
     */
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException
    {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    /**
     * @see ProtocolSocketFactory#createSocket(java.lang.String,int,java.net.InetAddress,int)
     */
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
            throws IOException, UnknownHostException
    {
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    /**
     * Attempts to get a new socket connection to the given host within the given time limit.
     * <p>
     * To circumvent the limitations of older JREs that do not support connect timeout a controller thread is executed.
     * The controller thread attempts to create a new socket within the given limit of time. If socket constructor does
     * not return until the timeout expires, the controller terminates and throws an {@link ConnectTimeoutException}
     * </p>
     * 
     * @param host
     *            the host name/IP
     * @param port
     *            the port on the host
     * @param params
     *            {@link HttpConnectionParams Http connection parameters}
     * @return Socket a new socket
     * @throws IOException
     *             if an I/O error occurs while creating the socket
     * @throws UnknownHostException
     *             if the IP address of the host cannot be determined
     */
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort,
            final HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException
    {
        if (params == null)
        {
            throw new IllegalArgumentException("Parameters may not be null");
        }

        int timeout = params.getConnectionTimeout();
        if (timeout == 0)
        {
            return createSocket(host, port, localAddress, localPort);
        }

        /** To be eventually deprecated when migrated to Java 1.4 or above */
        return ControllerThreadSocketFactory.createSocket(this, host, port, localAddress, localPort, timeout);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        return ((obj != null) && obj.getClass().equals(EasySSLProtocolSocketFactory.class));
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return EasySSLProtocolSocketFactory.class.hashCode();
    }

    /**
     * @return The SSLContext
     */
    private SSLContext getSSLContext()
    {
        if (this.sslcontext == null)
        {
            this.sslcontext = createEasySSLContext();
        }

        return this.sslcontext;
    }
}