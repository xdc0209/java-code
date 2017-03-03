package com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.api;

/**
 * Amazon WebService(AWS)请求Header扩展参数。
 */
public final class AwsHttpHeaders
{
    /**
     * AWS扩展Header前缀: {@value}
     */
    public static final String X_HEADER_PREFIX  = "X-AWS-";

    /**
     * AWS扩展Header: 时间戳，{@value}
     */
    public static final String X_DATE           = X_HEADER_PREFIX + "Date";

    /**
     * AWS扩展Header: Server，{@value}
     */
    public static final String X_SERVER         = X_HEADER_PREFIX + "Server";

    /**
     * AWS扩展Header: 认证参数，{@value}
     */
    public static final String X_AUTHORIZATION  = X_HEADER_PREFIX + "Authorization";

    /**
     * AWS增强型扩展Header参数(会参与鉴权认证)前缀: {@value}
     */
    public static final String XX_HEADER_PREFIX = "XX-IWS-";
}