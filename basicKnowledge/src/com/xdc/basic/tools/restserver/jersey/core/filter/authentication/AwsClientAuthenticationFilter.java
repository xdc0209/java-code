package com.xdc.basic.tools.restserver.jersey.core.filter.authentication;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import com.xdc.basic.tools.restserver.jersey.core.filter.authentication.credentials.Credentials;
import com.xdc.basic.tools.restserver.jersey.core.filter.authentication.sign.api.Signer;

public class AwsClientAuthenticationFilter implements ClientRequestFilter
{
    /**
     * Client访问凭证
     */
    private Credentials credentials;

    /**
     * 身份认证签名工具
     */
    private Signer      signer;

    /**
     * 构造一个新的客户端请求签名过滤器。
     * 
     * @param credentials
     *            Client访问凭证
     * @param signer
     *            身份认证签名工具
     */
    public AwsClientAuthenticationFilter(Credentials credentials, Signer signer)
    {
        this.credentials = credentials;
        this.signer = signer;
    }

    public Credentials getCredentials()
    {
        return credentials;
    }

    public void setCredentials(Credentials credentials)
    {
        this.credentials = credentials;
    }

    public Signer getSigner()
    {
        return signer;
    }

    public void setSigner(Signer signer)
    {
        this.signer = signer;
    }

    /**
     * 参考网上资料，实现AWS-S3摘要认证机制（客户端）（HTTP Digest增强）
     */
    @Override
    public void filter(ClientRequestContext context) throws IOException
    {
        String date = context.getHeaderString(HttpHeaders.DATE);
        if (null == date)
        {
            context.getHeaders().add(HttpHeaders.DATE, System.currentTimeMillis());
        }

        String signaturing = signer.sign(credentials, new AwsClientAuthenticationRequest(context));
        context.getHeaders().add(HttpHeaders.AUTHORIZATION, "AWS " + credentials.getAccessKey() + ":" + signaturing);
    }
}
