package com.xdc.basic.tools.restserver.jersey.core.filter.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.client.ClientRequestContext;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.tools.restserver.jersey.core.filter.authentication.sign.api.AuthenticationRequest;
import com.xdc.basic.tools.restserver.jersey.utils.ContextUtils;

/**
 * 客户端请求包装器。
 */
public class AwsClientAuthenticationRequest implements AuthenticationRequest
{
    /**
     * JAX-RS 2.0客户端请求上下文。
     */
    private final ClientRequestContext context;

    /**
     * 构造一个新的客户端请求包装。
     * 
     * @param context
     *            JAX-RS 2.0客户端请求上下文
     */
    public AwsClientAuthenticationRequest(ClientRequestContext context)
    {
        this.context = context;
    }

    @Override
    public String getMethod()
    {
        return context.getMethod();
    }

    @Override
    public String getPath(boolean decoded)
    {
        return context.getUri().getPath();
    }

    @Override
    public long getDataTime()
    {
        return context.getDate().getTime();
    }

    @Override
    public Date getDate()
    {
        return context.getDate();
    }

    @Override
    public Map<String, String> getQueryParams(boolean decoded)
    {
        Map<String, String> params = new LinkedHashMap<String, String>();

        String query = context.getUri().getRawQuery();
        if (null == query)
        {
            return params;
        }

        String[] tokens = query.split("&");
        for (String token : tokens)
        {
            if (!StringUtils.contains(token, "="))
            {
                continue;
            }

            String key = StringUtils.substringBefore(token, "=");
            String value = StringUtils.substringAfter(token, "=");

            if (decoded)
            {
                try
                {
                    key = URLDecoder.decode(key, "UTF-8");
                    value = URLDecoder.decode(value, "UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }

            params.put(key, value);
        }

        return params;
    }

    @Override
    public Map<String, String> getHeaders()
    {
        return ContextUtils.transform(context.getStringHeaders());
    }

    @Override
    public String getHeader(String headerKey)
    {
        return context.getHeaderString(headerKey);
    }
}
