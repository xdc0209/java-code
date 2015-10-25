package com.xdc.basic.tools.restclientbasedonhttpclient3;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.tools.restclientbasedonhttpclient3.constants.Constants;
import com.xdc.basic.tools.restclientbasedonhttpclient3.constants.HttpConstants;
import com.xdc.basic.tools.restclientbasedonhttpclient3.constants.HttpMethodType;
import com.xdc.basic.tools.restclientbasedonhttpclient3.constants.HttpProtocol;
import com.xdc.basic.tools.restclientbasedonhttpclient3.easyssl.EasySSLProtocolSocketFactory;
import com.xdc.basic.tools.restclientbasedonhttpclient3.message.Request;
import com.xdc.basic.tools.restclientbasedonhttpclient3.message.Response;
import com.xdc.basic.tools.restclientbasedonhttpclient3.message.RestClientException;
import com.xdc.basic.tools.restclientbasedonhttpclient3.tools.Base64Util;
import com.xdc.basic.tools.restclientbasedonhttpclient3.tools.BytesUtil;
import com.xdc.basic.tools.restclientbasedonhttpclient3.tools.IpTool;
import com.xdc.basic.tools.restclientbasedonhttpclient3.tools.JsonTool;
import com.xdc.basic.tools.restclientbasedonhttpclient3.tools.XmlTool;

public class RestClient
{
    private HttpProtocol protocol = HttpProtocol.HTTP;
    private String       host;
    private String       port;
    private String       authorization;

    public RestClient()
    {
        super();
    }

    public RestClient(HttpProtocol protocol, String host, String port, String user, String password)
    {
        super();
        if (!IpTool.isIpv4Port(port))
        {
            throw new IllegalArgumentException("Port [" + port + "] is not leagal. Port should be in [1,65535].");
        }

        if (StringUtils.isBlank(port))
        {
            port = protocol.getDefaultPort();
        }

        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.authorization = getBasicAuthorization(user, password);
    }

    private String getBasicAuthorization(String user, String password)
    {
        if (StringUtils.isBlank(user) || StringUtils.isBlank(password))
        {
            return null;
        }

        String encodeStr = Base64Util.encodeStr(user + ":" + password, Constants.Charset.UTF8);
        return HttpConstants.AUTH_BASIC + " " + encodeStr;
    }

    public Response handldeRequset(Request req) throws RestClientException
    {
        HttpMethod httpMethod = null;

        String url = getUrl(req);

        String method = req.getMethod();
        if (HttpMethodType.GET.toString().equalsIgnoreCase(method))
        {
            GetMethod getMethod = new GetMethod(url);
            httpMethod = getMethod;
        }
        else if (HttpMethodType.POST.toString().equalsIgnoreCase(method))
        {
            PostMethod postMethod = new PostMethod(url);
            StringRequestEntity requestEntity = null;
            try
            {
                requestEntity = new StringRequestEntity(req.getBody(), null, Constants.Charset.UTF8);
            }
            catch (UnsupportedEncodingException e)
            {
                handleException(e);
            }
            postMethod.setRequestEntity(requestEntity);
            httpMethod = postMethod;
        }
        else if (HttpMethodType.PUT.toString().equalsIgnoreCase(method))
        {
            PutMethod putMethod = new PutMethod(url);
            StringRequestEntity requestEntity = null;
            try
            {
                requestEntity = new StringRequestEntity(req.getBody(), null, Constants.Charset.UTF8);
            }
            catch (UnsupportedEncodingException e)
            {
                handleException(e);
            }
            putMethod.setRequestEntity(requestEntity);
            httpMethod = putMethod;
        }
        else if (HttpMethodType.DELETE.toString().equalsIgnoreCase(method))
        {
            DeleteMethod deleteMethod = new DeleteMethod(url);
            httpMethod = deleteMethod;
        }
        else
        {
            throw new UnsupportedOperationException("Method " + method + " is not support.");
        }

        configHeader(req, httpMethod);

        executeMethod(httpMethod);

        Response rsp = convertResponse(httpMethod);

        httpMethod.releaseConnection();

        return rsp;
    }

    private void configHeader(Request req, HttpMethod httpMethod)
    {
        if (StringUtils.equalsIgnoreCase(req.getBodyType(), Constants.BodyType.json))
        {
            httpMethod.setRequestHeader(HttpConstants.CONTENT_TYPE, HttpConstants.ContentType.JSON + ";"
                    + HttpConstants.Charset.UTF8);
        }
        else if (StringUtils.equalsIgnoreCase(req.getBodyType(), Constants.BodyType.xml))
        {
            httpMethod.setRequestHeader(HttpConstants.CONTENT_TYPE, HttpConstants.ContentType.XML + ";"
                    + HttpConstants.Charset.UTF8);
        }
        else
        {
            // 没有则不设置类型
        }

        if (StringUtils.isNotBlank(authorization))
        {
            httpMethod.setRequestHeader(HttpConstants.AUTH, authorization);
        }
    }

    private String getUrl(Request req)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol.getName());
        sb.append("://");
        sb.append(host);
        if (StringUtils.isNotBlank(port))
        {
            sb.append(":");
            sb.append(port);
        }
        sb.append(req.getUrl());

        return sb.toString();
    }

    private Response convertResponse(HttpMethod httpMethod) throws RestClientException
    {
        int statusCode = httpMethod.getStatusLine().getStatusCode();

        byte[] responseBody = null;
        try
        {
            responseBody = httpMethod.getResponseBody();
        }
        catch (IOException e)
        {
            handleException(e);
        }
        String body = BytesUtil.bytes2String(responseBody, Constants.Charset.UTF8);

        String bodyType = null;
        if (StringUtils.contains(httpMethod.getResponseHeader(HttpConstants.CONTENT_TYPE).getValue(),
                HttpConstants.ContentType.JSON))
        {
            body = JsonTool.format(body);
            bodyType = Constants.BodyType.json;
        }
        if (StringUtils.contains(httpMethod.getResponseHeader(HttpConstants.CONTENT_TYPE).getValue(),
                HttpConstants.ContentType.XML))
        {
            body = XmlTool.format(body);
            bodyType = Constants.BodyType.xml;
        }

        return new Response(statusCode, bodyType, body);
    }

    private void executeMethod(HttpMethod httpMethod) throws RestClientException
    {
        try
        {
            Protocol protocol = new Protocol("https", new EasySSLProtocolSocketFactory(), Integer.valueOf(port));
            Protocol.registerProtocol("https", protocol);

            HttpClient httpClient = new HttpClient();

            // 与上面的语句作用一样，设置主机协议，主要是实现ssl自签名
            // httpClient.getHostConfiguration().setHost(host, Integer.valueOf(port), protocol);

            // 设置超时时间5s
            httpClient.getParams().setSoTimeout(5000);

            httpClient.executeMethod(httpMethod);
        }
        catch (Exception e)
        {
            handleException(e);
        }
    }

    private void handleException(Throwable e) throws RestClientException
    {
        e.printStackTrace();
        throw new RestClientException("Invoke rest client failed.", e);
    }
}