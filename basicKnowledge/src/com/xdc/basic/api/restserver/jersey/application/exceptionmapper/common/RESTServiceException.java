package com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

/**
 * JAX-RS服务错误相关的异常，用于包装应用层错误信息，方便JAX-RS框架输出客户端更友好的响应消息。
 * 其实java的ws内置很多异常，但是太多，又不太好用，所以定义此异常。
 * 
 * @see javax.ws.rs.WebApplicationException
 */
public class RESTServiceException extends RuntimeException
{
    private static final long   serialVersionUID = -757315856360441584L;

    /**
     * 返回给客户端的Response的状态码
     */
    private Status              status           = null;

    /**
     * 返回给客户端的Response的body
     */
    private Map<String, Object> details          = new LinkedHashMap<String, Object>();

    public static RESTServiceException badRequest(String errorMessage)
    {
        // 400：请求体的header或body非法
        return new RESTServiceException(Status.BAD_REQUEST, "xdc.api.error.common.BadRequest", errorMessage);
    }

    public static RESTServiceException unauthorized(String errorMessage)
    {
        // 401：鉴权失败
        return new RESTServiceException(Status.UNAUTHORIZED, "xdc.api.error.common.Unauthorized", errorMessage);
    }

    public static RESTServiceException resourceNotExist(String errorMessage)
    {
        // 404：将资源不存在异常转换为http标准的"Not Found"(404)异常。
        return new RESTServiceException(Status.NOT_FOUND, "xdc.api.error.common.ResourceNotExist", errorMessage);
    }

    public static RESTServiceException resourceNotUnique(String errorMessage)
    {
        // 409：将资源不唯一异常映射为http标准的"Conflict"(409)异常代码.
        return new RESTServiceException(Status.CONFLICT, "xdc.api.error.common.ResourceNotUnique", errorMessage);
    }

    public static RESTServiceException internalServerError(String errorMessage)
    {
        // 500：服务器内部错误，服务器遇到错误，无法完成请求。
        return new RESTServiceException(Status.INTERNAL_SERVER_ERROR, "xdc.api.error.common.InternalServerError",
                errorMessage);
    }

    public static RESTServiceException internalServerError(String errorMessage, Throwable cause)
    {
        // 503：服务不可用，服务器目前无法使用(由于超载或停机维护)。 通常，这只是暂时状态。
        return new RESTServiceException(Status.INTERNAL_SERVER_ERROR, "xdc.api.error.common.InternalServerError",
                errorMessage, cause);
    }

    public static RESTServiceException serviceUnavailable(String errorMessage)
    {
        return new RESTServiceException(Status.SERVICE_UNAVAILABLE, "xdc.api.error.common.ServiceUnvailable",
                errorMessage);
    }

    public RESTServiceException(Status status)
    {
        this(status, null, null);
    }

    public RESTServiceException(Status status, String errorId, String errorMessage)
    {
        this(status, errorId, errorMessage, null);
    }

    public RESTServiceException(Status status, String errorId, String errorMessage, Throwable cause)
    {
        super(errorId + ": " + errorMessage, cause);

        this.status = status;

        if (StringUtils.isNotEmpty(errorId))
        {
            this.details.put("errorId", errorId);
        }

        if (StringUtils.isNotEmpty(errorMessage))
        {
            this.details.put("errorMessage", errorMessage);
        }
    }

    public Status getStatus()
    {
        return status;
    }

    public Map<String, Object> getDetails()
    {
        return details;
    }

    /**
     * errorId与errorMessage字段为必备，可附加其他参数，目的是帮助客户端理解导致错误的原因，后续在恰当的时间能够重建请求。
     */
    public RESTServiceException addDetail(String name, Object value)
    {
        details.put(name, value);
        return this;
    }
}
