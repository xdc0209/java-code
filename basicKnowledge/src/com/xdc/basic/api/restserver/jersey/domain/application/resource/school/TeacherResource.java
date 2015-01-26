package com.xdc.basic.api.restserver.jersey.domain.application.resource.school;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("school/tearcher")
public class TeacherResource
{
    /**
     * 用于测试的消息回显方法.
     */
    @GET
    @Path("echo/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String echo(@PathParam("message") String message)
    {
        return message;
    }
}
