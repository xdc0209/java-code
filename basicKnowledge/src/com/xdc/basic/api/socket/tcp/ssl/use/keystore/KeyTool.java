package com.xdc.basic.api.socket.tcp.ssl.use.keystore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

import org.junit.Test;

import com.xdc.basic.skills.GetPath;

/**
 * 摘自：http://developer.51cto.com/art/200512/13785.htm
 */
public class KeyTool
{
    /**
     * 一 从证书文件中读取证书
     */
    @Test
    public void readCertificateFromFile() throws CertificateException, FileNotFoundException
    {
        String curPath = GetPath.getRelativePath();

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(curPath + "selfsignedcert.cer");
        Certificate c = cf.generateCertificate(in);
        System.out.println(c);
    }

    /**
     * 二 从密钥库中读取证书
     */
    @Test
    public void readCertificateFromKeystore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException
    {
        String curPath = GetPath.getRelativePath();

        String password = "123456";
        FileInputStream in = new FileInputStream(curPath + "keystore.jks");

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(in, password.toCharArray());

        Certificate c = ks.getCertificate("certificatekey");
        System.out.println(c);
    }

    /**
     * 三 JAVA程序列出密钥库所有条目
     */
    @Test
    public void listAliases() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
    {
        String curPath = GetPath.getRelativePath();

        String password = "123456";
        FileInputStream in = new FileInputStream(curPath + "keystore.jks");

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(in, password.toCharArray());

        Enumeration<String> e = ks.aliases();
        while (e.hasMoreElements())
        {
            System.out.println(e.nextElement());
        }
    }

    /**
     * 四 JAVA程序修改密钥库口令
     */
    @Test
    public void modifyKeyStorePassword() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException
    {
        String curPath = GetPath.getRelativePath();

        // 为了不对密钥库进行更改，新密码和旧密码一样
        String oldPassword = "123456";
        String newPassword = "123456";

        FileInputStream in = new FileInputStream(curPath + "keystore.jks");

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(in, oldPassword.toCharArray());
        in.close();

        FileOutputStream output = new FileOutputStream(curPath + "keystore.jks");
        ks.store(output, newPassword.toCharArray());
        output.close();
    }

}
