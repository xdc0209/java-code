package com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 将DN不唯一异常映射为http标准的"Conflict"(409)异常代码.
 */
@Provider
public class IdNotUniqueMapper implements ExceptionMapper<IdNotUniqueException>
{
    @Override
    public Response toResponse(IdNotUniqueException exception)
    {
        Response response = Response.status(Status.CONFLICT).entity(exception.getMessage()).build();
        return response;
    }
}