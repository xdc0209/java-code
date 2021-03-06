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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

import org.apache.commons.lang3.ArrayUtils;

import com.xdc.basic.commons.PathUtil;

public class Server
{
    public static void main(String[] args) throws Exception
    {
        String keystorePath = PathUtil.getRelativePath() + "keystore/keystore.jks";
        String keystorePass = "123456";

        ServerSocket serverSocket = createSSLServerSocket(8443, keystorePath, keystorePass);
        System.out.println("服务器对8443端口正在进行监听...");

        ExecutorService exec = Executors.newFixedThreadPool(10);
        while (true)
        {
            Socket sc = serverSocket.accept();
            System.out.println("接受并处理scoket连接： " + sc.getInetAddress().getHostAddress() + ":" + sc.getPort());

            SocketHanlder scocketHanlder = new SocketHanlder(sc);
            exec.submit(scocketHanlder);
        }
    }

    private static SSLServerSocket createSSLServerSocket(int port, String keystorePath, String keystorePass)
            throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException,
            FileNotFoundException, UnrecoverableKeyException, KeyManagementException
    {
        // 获取密钥库工具（java支持JKS、JCEKS、PKCS12。 java默认类型为JKS。 JCEKS在安全级别上要比JKS强。）
        KeyStore ks = KeyStore.getInstance("JCEKS");
        // 读取密钥库文件，需保证密钥库文件的类型与密钥库工具的类型的一致，否则会报异常。（JCEKS读取JKS文件不会报错）
        ks.load(new FileInputStream(keystorePath), null);

        // 获取管理JCEKS密钥库的X.509密钥管理器
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keystorePass.toCharArray());

        // 获取一个SSLContext实例（TSL优于SSL）
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kmf.getKeyManagers(), null, null);

        ServerSocketFactory factory = context.getServerSocketFactory();
        ServerSocket serverSocket = factory.createServerSocket(port);

        // 使用单向验证，服务端不必校验客户端身份
        ((SSLServerSocket) serverSocket).setNeedClientAuth(false);

        // 提高安全，使用安全的协议和套件
        setEnabledProtocols((SSLServerSocket) serverSocket);
        setEnabledCipherSuites((SSLServerSocket) serverSocket);

        return (SSLServerSocket) serverSocket;
    }

    private static void setEnabledProtocols(SSLServerSocket serverSocket)
    {
        // 支持的协议
        String[] supportedProtocols = serverSocket.getSupportedProtocols();

        // 安全的协议: SSLv3、TLSv1已被公认为不安全的协议，禁止使用
        List<String> safeProtocols = new ArrayList<String>();
        safeProtocols.add("SSLv2Hello"); // 为了兼容性服务端需保留此协议，目前此协议还未发现漏洞
        safeProtocols.add("TLSv1.1");
        safeProtocols.add("TLSv1.2");

        List<String> protocolsToUse = new ArrayList<String>();
        for (String safeProtocol : safeProtocols)
        {
            if (ArrayUtils.contains(supportedProtocols, safeProtocol))
            {
                protocolsToUse.add(safeProtocol);
            }
        }
        if (protocolsToUse.isEmpty())
        {
            println("No availalbe safe ssl protocols to use, use default: "
                    + ArrayUtils.toString(serverSocket.getEnabledProtocols()));
            return;
        }

        String[] enabledProtocols = protocolsToUse.toArray(new String[protocolsToUse.size()]);
        println("Change enabled ssl protocols to: " + ArrayUtils.toString(enabledProtocols));

        serverSocket.setEnabledProtocols(enabledProtocols);
    }

    private static void setEnabledCipherSuites(SSLServerSocket serverSocket)
    {
        // 支持的加密套件
        String[] supportedCipherSuites = serverSocket.getSupportedCipherSuites();

        // 安全的加密套件
        List<String> safeCipherSuites = new ArrayList<String>();
        safeCipherSuites.add("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA");
        safeCipherSuites.add("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA");
        safeCipherSuites.add("SSL_RSA_WITH_3DES_EDE_CBC_SHA");
        safeCipherSuites.add("TLS_DHE_DSS_WITH_AES_128_CBC_SHA");
        safeCipherSuites.add("TLS_DHE_RSA_WITH_AES_128_CBC_SHA");
        safeCipherSuites.add("TLS_RSA_WITH_AES_128_CBC_SHA");

        List<String> cipherSuitesToUse = new ArrayList<String>();
        for (String safeCipherSuite : safeCipherSuites)
        {
            if (ArrayUtils.contains(supportedCipherSuites, safeCipherSuite))
            {
                cipherSuitesToUse.add(safeCipherSuite);
            }
        }
        if (cipherSuitesToUse.isEmpty())
        {
            println("No availalbe safe ssl cipher suites to use, use default: "
                    + ArrayUtils.toString(serverSocket.getEnabledCipherSuites()));
            return;
        }

        String[] enabledCipherSuites = cipherSuitesToUse.toArray(new String[cipherSuitesToUse.size()]);
        println("Change enabled ssl cipher suites to: " + ArrayUtils.toString(enabledCipherSuites));

        serverSocket.setEnabledCipherSuites(enabledCipherSuites);
    }

    private static void println(String x)
    {
        System.out.println(x);
    }
}
