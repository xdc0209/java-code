package com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 将MO对象不存在异常转换为http标准的"Not Found"(404)异常.
 */
@Provider
public class ResourceNotExistMapper implements ExceptionMapper<ResourceNotExistException>
{
    @Override
    public Response toResponse(ResourceNotExistException exception)
    {
        Response response = Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
        return response;
    }
}