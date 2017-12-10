package com.xdc.basic.api.restserver.jersey.core.filter.authentication;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api.AuthenticationRequest;
import com.xdc.basic.api.restserver.jersey.utils.ContextUtils;

/**
 * 服务端请求包装器。(注意：这个是从客户端发过来的请求。)
 */
public class AwsServerAuthenticationRequest implements AuthenticationRequest
{
    /**
     * JAX-RS 2.0服务端请求上下文。
     */
    private final ContainerRequestContext context;

    /**
     * 构造一个新的服务端请求包装。
     * 
     * @param context
     *            JAX-RS 2.0服务端请求上下文
     */
    public AwsServerAuthenticationRequest(ContainerRequestContext context)
    {
        this.context = context;
    }

    @Override
    public final String getMethod()
    {
        return context.getMethod();
    }

    @Override
    public final String getPath(boolean decoded)
    {
        return context.getUriInfo().getAbsolutePath().getPath();
    }

    @Override
    public long getDataTime()
    {
        String dateHeader = context.getHeaderString(HttpHeaders.DATE);
        return Long.valueOf(dateHeader);
    }

    @Override
    public Date getDate()
    {
        return context.getDate();
    }

    @Override
    public Map<String, String> getQueryParams(boolean decoded)
    {
        return ContextUtils.transform(context.getUriInfo().getQueryParameters(decoded));
    }

    @Override
    public Map<String, String> getHeaders()
    {
        return ContextUtils.transform(context.getHeaders());
    }

    @Override
    public String getHeader(String headerKey)
    {
        return context.getHeaderString(headerKey);
    }
}
