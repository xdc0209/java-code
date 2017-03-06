package com.xdc.basic.tools.restclient;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.xdc.basic.commons.network.IpUtil;
import com.xdc.basic.tools.restclient.constants.Constants;
import com.xdc.basic.tools.restclient.constants.HttpConstants;
import com.xdc.basic.tools.restclient.constants.HttpMethod;
import com.xdc.basic.tools.restclient.constants.HttpProtocol;
import com.xdc.basic.tools.restclient.message.Request;
import com.xdc.basic.tools.restclient.message.Response;
import com.xdc.basic.tools.restclient.message.RestClientException;
import com.xdc.basic.tools.restclient.tools.JsonTool;
import com.xdc.basic.tools.restclient.tools.XmlTool;

public class RestClient
{
    private HttpProtocol protocol = HttpProtocol.HTTP;
    private String       host;
    private String       port;
    private String       authorization;

    public RestClient(HttpProtocol protocol, String host, String port, String user, String password)
    {
        // port不为空，也不是一个合法的端口，抛异常。
        if (port != null && !IpUtil.isIpv4Port(port))
        {
            throw new IllegalArgumentException("Port [" + port + "] is not leagal. Port should be in [0, 65535].");
        }

        if (port == null)
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

        String encodeStr = Base64
                .encodeBase64String(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(user + ":" + password));
        return HttpConstants.AUTH_BASIC + " " + encodeStr;
    }

    public Response handldeRequset(Request req) throws RestClientException
    {
        HttpRequestBase request = null;

        String url = getUrl(req);

        String method = req.getMethod();
        if (HttpMethod.GET.toString().equalsIgnoreCase(method))
        {
            HttpGet httpGet = new HttpGet(url);
            request = httpGet;
        }
        else if (HttpMethod.POST.toString().equalsIgnoreCase(method))
        {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(req.getBody(), Charsets.UTF_8));
            request = httpPost;
        }
        else if (HttpMethod.PUT.toString().equalsIgnoreCase(method))
        {
            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(new StringEntity(req.getBody(), Charsets.UTF_8));
            request = httpPut;
        }
        else if (HttpMethod.DELETE.toString().equalsIgnoreCase(method))
        {
            HttpDelete httpDelete = new HttpDelete(url);
            request = httpDelete;
        }
        else
        {
            throw new UnsupportedOperationException("Method " + method + " is not support.");
        }

        configRequest(req, request);

        CloseableHttpResponse response = execute(request);
        Response rsp = convertResponse(response);
        return rsp;
    }

    private void configRequest(Request req, HttpRequestBase request)
    {
        if (StringUtils.equalsIgnoreCase(req.getBodyType(), Constants.BodyType.json))
        {
            request.addHeader(HttpConstants.CONTENT_TYPE,
                    HttpConstants.ContentType.JSON + ";" + HttpConstants.Charset.UTF8);
        }
        else if (StringUtils.equalsIgnoreCase(req.getBodyType(), Constants.BodyType.xml))
        {
            request.addHeader(HttpConstants.CONTENT_TYPE,
                    HttpConstants.ContentType.XML + ";" + HttpConstants.Charset.UTF8);
        }
        else
        {
            // 没有则不设置类型
        }

        if (StringUtils.isNotBlank(authorization))
        {
            request.addHeader(HttpConstants.AUTH, authorization);
        }

        // 设置请求和传输超时时间5s
        RequestConfig requestconfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        request.setConfig(requestconfig);
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

    private Response convertResponse(CloseableHttpResponse response) throws RestClientException
    {
        int statusCode = response.getStatusLine().getStatusCode();

        String body = null;
        try
        {
            body = EntityUtils.toString(response.getEntity());
        }
        catch (Exception e)
        {
            handleException(e);
        }

        String bodyType = null;
        if (StringUtils.contains(response.getEntity().getContentType().getValue(), HttpConstants.ContentType.JSON))
        {
            body = JsonTool.format(body);
            bodyType = Constants.BodyType.json;
        }
        if (StringUtils.contains(response.getEntity().getContentType().getValue(), HttpConstants.ContentType.XML))
        {
            body = XmlTool.format(body);
            bodyType = Constants.BodyType.xml;
        }

        return new Response(statusCode, bodyType, body);
    }

    private CloseableHttpResponse execute(HttpUriRequest request) throws RestClientException
    {
        CloseableHttpResponse response = null;
        try
        {
            SSLContextBuilder builder = new SSLContextBuilder();
            SSLContext sslContext = builder.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            response = httpclient.execute(request);
        }
        catch (Exception e)
        {
            handleException(e);
        }
        return response;
    }

    private void handleException(Throwable e) throws RestClientException
    {
        e.printStackTrace();
        throw new RestClientException("Invoke rest client failed.", e);
    }
}