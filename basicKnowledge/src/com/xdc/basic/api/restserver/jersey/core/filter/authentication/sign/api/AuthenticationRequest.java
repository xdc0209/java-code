package com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api;

import java.util.Date;
import java.util.Map;

/**
 * HTTP请求包装器。包装JAX-RS 2.0客户端/服务端及javax HTTP请求，为过滤器提供统一的处理接口。
 */
public interface AuthenticationRequest
{
    /**
     * 查询HTTP请求方法。
     * 
     * @return HTTP请求方法
     */
    String getMethod();

    /**
     * 查询HTTP请求的资源URI(绝对路径)。
     * 
     * @param decoded
     *            是否返回解码后的URI，如果为true则表示返回解码后的URI，否则返回编码后的URI
     * @return HTTP请求的资源URI
     */
    String getPath(boolean decoded);

    /**
     * 查询请求发起时的时间戳，来自于HTTP请求的Header参数({@link HttpHeaders#DATE})。
     * 
     * @return 请求发起时的时间戳
     */
    long getDataTime();

    /**
     * 查询请求发起时的时间，来自于HTTP请求的Header参数({@link HttpHeaders#DATE})。
     * 
     * @return 请求发起时的时间
     */
    Date getDate();

    /**
     * 获取请求中包含的查询参数列表。
     * 
     * @param decoded
     *            是否针对返回的参数名与值进行URL解码
     * @return 请求中包含的查询参数列表
     */
    Map<String, String> getQueryParams(boolean decoded);

    /**
     * 查询请求中包含的Header参数列表。
     * 
     * @return 请求中包含的Header参数列表
     */
    Map<String, String> getHeaders();

    /**
     * 查询指定的Header参数。
     * 
     * @param headerKey
     *            目标Header参数的Key
     * @return 指定的Header参数值，如果目标参数不存在，则返回null
     */
    String getHeader(String headerKey);
}
