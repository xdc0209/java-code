package com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RESTServiceExceptionMapper implements ExceptionMapper<RESTServiceException>
{
    @Override
    public Response toResponse(RESTServiceException exception)
    {
        return Response.status(exception.getStatus()).entity(exception.getDetails()).build();
    }
}
