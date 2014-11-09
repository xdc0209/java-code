package com.xdc.basic.api.socket.tcp.ssl.use;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

import com.xdc.basic.skills.GetPath;

public class Server
{
    public static void main(String[] args) throws Exception
    {
        String keystorePath = GetPath.getRelativePath() + "keystore/keystore.jks";
        String keystorePass = "123456";

        ServerSocket serverSocket = createSSLServerSocket(8443, keystorePath, keystorePass);
        System.out.println("服务器对8443端口正在进行监听...");

        ExecutorService exec = Executors.newFixedThreadPool(10);
        while (true)
        {
            Socket sc = serverSocket.accept();
            System.out.println("接受并处理scoket连接： " + sc.getInetAddress().getHostAddress() + ":" + sc.getLocalPort());

            SocketHanlder scocketHanlder = new SocketHanlder(sc);
            exec.submit(scocketHanlder);
        }
    }

    private static SSLServerSocket createSSLServerSocket(int port, String keystorePath, String keystorePass)
            throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException,
            FileNotFoundException, UnrecoverableKeyException, KeyManagementException
    {
        // 创建JCEKS密钥库（JCEKS在安全级别上要比JKS强）
        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(new FileInputStream(keystorePath), null);

        // 创建管理JCEKS密钥库的X.509密钥管理器
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keystorePass.toCharArray());

        // 获取一个SSLContext实例（TSL优于SSL）
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kmf.getKeyManagers(), null, null);

        ServerSocketFactory factory = context.getServerSocketFactory();
        ServerSocket serverSocket = factory.createServerSocket(port);

        // 使用单向验证，服务端不必校验客户端身份
        ((SSLServerSocket) serverSocket).setNeedClientAuth(false);

        return (SSLServerSocket) serverSocket;
    }
}
