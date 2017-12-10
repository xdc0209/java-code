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
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * EasyX509TrustManager unlike default {@link X509TrustManager} accepts self-signed certificates.
 * 
 * @author <a href="mailto:adrian.sutton@ephox.com">Adrian Sutton </a>
 * @author <a href="mailto:oleg@ural.ru">Oleg Kalnichevski </a>
 */
public class EasyX509TrustManager implements X509TrustManager
{
    private X509TrustManager standardTrustManager = null;

    /**
     * Constructor for EasyX509TrustManager.
     * 
     * @param keystore
     *            In-memory collection of keys and certificates
     * @throws NoSuchAlgorithmException
     *             In case of an error
     * @throws KeyStoreException
     *             In case of an error
     */
    public EasyX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException
    {
        super();
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        factory.init(keystore);

        TrustManager[] trustmanagers = factory.getTrustManagers();
        if (trustmanagers.length == 0)
        {
            throw new NoSuchAlgorithmException("No trust manager found");
        }
        this.standardTrustManager = (X509TrustManager) trustmanagers[0];
    }

    /**
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkClientTrusted(X509Certificate[] chain, String authType)
    {
        return;
    }

    /**
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkServerTrusted(X509Certificate[] chain, String authType)
    {
        return;
    }

    /**
     * @see X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers()
    {
        if (standardTrustManager != null)
        {
            return standardTrustManager.getAcceptedIssuers();
        }

        return new X509Certificate[] {};
    }
}
