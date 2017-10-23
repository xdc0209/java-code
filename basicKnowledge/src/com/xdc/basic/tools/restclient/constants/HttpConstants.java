package com.xdc.basic.tools.restclient.constants;

public interface HttpConstants
{
    /**
     * BASE认证头域
     */
    String AUTH         = "Authorization";

    /**
     * BASE认证头域值的前辍
     */
    String AUTH_BASIC   = "Basic";

    /**
     * http协议内容格式
     */
    String CONTENT_TYPE = "Content-type";

    interface ContentType
    {
        String XML          = "application/xml";
        String XML_T        = "text/xml";
        String JSON         = "application/json";
        String JSON_T       = "text/json";
        String HTML         = "text/html";
        String TEXT_PLAIN   = "text/plain";
        String OCTET_STREAM = "application/octet-stream";
    }

    interface Charset
    {
        String UTF8 = "charset=UTF-8";
    }
}
