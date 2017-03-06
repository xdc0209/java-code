package com.xdc.basic.api.restserver.jersey.core.filter.authentication.credentials;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.binary.Base64;

import com.xdc.basic.api.restserver.jersey.core.config.RestServerConfig;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.credentials.Credentials.Status;

/**
 * 客户/用户身份凭证管理。
 * （1）生成：第三应用开发者到AWS凭证管理页面申请，然后写入自己的应用中，用于与AWS交互时的鉴权。
 * （2）立即销毁：第三方应用保存不当，凭证泄露，到AWS凭证管理页面申请立即销毁此凭证。销毁只是把凭证的状态改为销毁状态，并不会删除。销毁后不能再启动。
 * （3）过期销毁：每个凭证都有一个有效期，过期后系统把凭证的状态改为销毁。
 */
public class CredentialsManager
{
    private static Map<String, Credentials> credentialsMap = new ConcurrentHashMap<String, Credentials>();

    static
    {
        Credentials credentials = new Credentials(RestServerConfig.getAccessKey(), RestServerConfig.getSecretKey());
        credentialsMap.put(credentials.getAccessKey(), credentials);
    }

    public static Credentials createCredentials()
    {
        SecureRandom secureRandom = new SecureRandom();

        byte[] accessKey = new byte[12];
        secureRandom.nextBytes(accessKey);

        byte[] secretKey = new byte[32];
        secureRandom.nextBytes(secretKey);

        Credentials credentials = new Credentials(Base64.encodeBase64String(accessKey),
                Base64.encodeBase64String(secretKey));

        credentialsMap.put(credentials.getAccessKey(), credentials);

        return clone(credentials);
    }

    public static void destroyCredentials(String accessKey)
    {
        Credentials credentials = credentialsMap.get(accessKey);
        if (credentials == null)
        {
            return;
        }

        credentials.refreshStatus();
        credentials.setStatus(Status.ImmediateDestroy);
    }

    public static Credentials getCredentials(String accessKey)
    {
        Credentials credentials = credentialsMap.get(accessKey);
        if (credentials == null)
        {
            return null;
        }

        credentials.refreshStatus();
        return clone(credentials);
    }

    public static List<Credentials> getAllCredentials()
    {
        List<Credentials> credentialsList = new ArrayList<Credentials>();
        for (Credentials credentials : credentialsMap.values())
        {
            credentials.refreshStatus();
            credentialsList.add(clone(credentials));
        }

        return credentialsList;
    }

    /**
     * 克隆副本，只返回副本，防止外部更改凭证状态。
     */
    private static Credentials clone(Credentials credentials)
    {
        try
        {
            return credentials.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
