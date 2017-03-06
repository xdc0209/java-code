package com.xdc.basic.api.restserver.jersey.core.filter.authentication;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common.RESTServiceException;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.credentials.Credentials;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.credentials.CredentialsManager;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.AuthenticationRequest;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.AwsHttpHeaders;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.Signer;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AwsServerAuthenticationFilter implements ContainerRequestFilter
{
    /**
     * 基于时间窗(ServerTime - RequestTime)判断请求是否有效的时间窗默认值(单位：毫秒): 缺省值10分钟。
     */
    private static long timeWindow = 10 * 60 * 1000;

    /**
     * 身份认证签名工具
     */
    private Signer      signer;

    public AwsServerAuthenticationFilter(Signer signer)
    {
        super();
        this.signer = signer;
    }

    /**
     * 参考网上资料，实现AWS-S3摘要认证机制（服务端）（HTTP Digest增强）
     */
    @Override
    public void filter(ContainerRequestContext context) throws IOException
    {
        final long invalidRequestTime = -1;

        long requestTime = invalidRequestTime;

        // 优先选择X-IWS-Date参数
        String dateHeader = context.getHeaderString(AwsHttpHeaders.X_DATE);
        if (null == dateHeader)
        {
            // 没有X-IWS-Date参数，选择Date参数
            dateHeader = context.getHeaderString(HttpHeaders.DATE);
            if (null == dateHeader)
            {
                throw RESTServiceException.badRequest("No timestamp header in request.");
            }
        }

        requestTime = NumberUtils.toLong(dateHeader, invalidRequestTime);
        if (requestTime == invalidRequestTime)
        {
            throw RESTServiceException.badRequest("Invalid timestamp header in request.");
        }

        long serverTime = System.currentTimeMillis();
        if (Math.abs(serverTime - requestTime) > timeWindow)
        {
            // 超时上下浮动范围，抛出异常
            throw RESTServiceException.badRequest("Expired timestamp header in request.");
        }

        authenticate(new AwsServerAuthenticationRequest(context));
    }

    public void authenticate(AuthenticationRequest request) throws NotAuthorizedException
    {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (null == authHeader)
        {
            throw RESTServiceException.unauthorized("No authorization header in request.");
        }

        // 同时支持http与https，二者使用的签名验证机制不同
        String[] tokens = authHeader.split("\\ |:");
        if (tokens.length == 3)
        {
            String accessKey = tokens[1];
            String signaturedFromRequest = tokens[2];

            Credentials credentials = CredentialsManager.getCredentials(accessKey);

            String signatured = signer.sign(credentials, request);

            if (null == signatured || !StringUtils.equals(signatured, signaturedFromRequest))
            {
                throw RESTServiceException.unauthorized("Authorize failed because of signature matched failed.");
            }
        }
        else
        {
            throw RESTServiceException.unauthorized("Invalid authorization header in request.");
        }
    }
}