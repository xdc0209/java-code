package com.xdc.basic.tools.restclient3;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.tools.restclient3.constants.Constants;
import com.xdc.basic.tools.restclient3.constants.HttpConstants;
import com.xdc.basic.tools.restclient3.constants.HttpMethods;
import com.xdc.basic.tools.restclient3.constants.HttpProtocol;
import com.xdc.basic.tools.restclient3.message.Request;
import com.xdc.basic.tools.restclient3.message.Response;
import com.xdc.basic.tools.restclient3.message.RestClientException;
import com.xdc.basic.tools.restclient3.tools.IpTool;

/*
 * 如果是编写的一个通用的客户端，可以用于支持访问所有的HTTP及HTTPS协议请求，这个时候SSL自签名就非常管用了，如soupUI，它是一款用于WEBSERVICE的性能及压力测试工具，可以访问所有的HTTPS请求，并且不需要我们指定trustStore。通过查看其源代码，原来它使用的就是SSL自签名的实现类org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory，有了它，在访问任何HTTPS的时候都不需要指定trustStore了。
 * 当然这个是有安全问题的，这个自签名的API中也有明确的提示，尽量不要用于生产环境，用于平时的测试过程中是可以的。使用是非常简单的，只需要在发起请求前将SSL自签名API注册到Protocol中即可：
 * 
 * 
 * 
 */
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
            throw new IllegalArgumentException("Port is not leagal. Port should be in [1,65535]. Port = " + port);
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

        String encodeStr = Base64.encodeBase64String(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(user
                + ":" + password));
        return HttpConstants.AUTH_BASIC + " " + encodeStr;
    }

    public Response handldeRequset(Request req) throws RestClientException
    {
        HttpMethod httpMethod = null;

        String url = getUrl(req);

        String method = req.getMethod();
        if (HttpMethods.GET.toString().equalsIgnoreCase(method))
        {
            GetMethod getMethod = new GetMethod(url);
            httpMethod = getMethod;
        }
        else if (HttpMethods.POST.toString().equalsIgnoreCase(method))
        {
            PostMethod postMethod = new PostMethod(url);
            StringRequestEntity requestEntity = new StringRequestEntity(req.getBody(), null, "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            httpMethod = postMethod;
        }
        else if (HttpMethods.PUT.toString().equalsIgnoreCase(method))
        {
            PutMethod putMethod = new PutMethod(url);
            StringRequestEntity requestEntity = new StringRequestEntity(req.getBody(), null, "UTF-8");
            putMethod.setRequestEntity(requestEntity);
            httpMethod = putMethod;
        }
        else if (HttpMethods.DELETE.toString().equalsIgnoreCase(method))
        {
            DeleteMethod deleteMethod = new DeleteMethod(url);
            httpMethod = deleteMethod;
        }
        else
        {
            throw new UnsupportedOperationException("Method " + method + " is not support.");
        }

        configRequest(req, request);

        CloseableHttpResponse response = execute(httpMethod);
        Response rsp = convertResponse(response);

        httpMethod.releaseConnection();
        return rsp;
    }

    private void configRequest(Request req, HttpMethod httpMethod)
    {
        if (StringUtils.equalsIgnoreCase(req.getBodyType(), Constants.BodyType.json))
        {
            httpMethod.addHeader(HttpConstants.CONTENT_TYPE, HttpConstants.ContentType.JSON + ";"
                    + HttpConstants.Charset.UTF_8);
        }
        else if (StringUtils.equalsIgnoreCase(req.getBodyType(), Constants.BodyType.xml))
        {
            request.addHeader(HttpConstants.CONTENT_TYPE, HttpConstants.ContentType.XML + ";"
                    + HttpConstants.Charset.UTF_8);
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

    private int execute(HttpMethod method) throws RestClientException
    {
        try
        {
            String url = "https://payment.cib.com.cn/payment/api/rest";
            Protocol myhttps = new Protocol("https", new EasySSLProtocolSocketFactory(), Integer.valueOf(port));
            Protocol.registerProtocol("https", myhttps);

            HttpClient http = new HttpClient();
            return http.executeMethod(method);
        }
        catch (Exception e)
        {
            handleException(e);
        }

        return 0;
    }

    private void handleException(Throwable e) throws RestClientException
    {
        e.printStackTrace();
        throw new RestClientException("Invoke rest client failed.", e);
    }
}