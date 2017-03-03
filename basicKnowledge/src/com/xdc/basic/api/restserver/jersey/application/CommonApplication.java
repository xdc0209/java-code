package com.xdc.basic.api.restserver.jersey.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common.RESTServiceExceptionMapper;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.AwsServerAuthenticationFilter;
import com.xdc.basic.api.restserver.jersey.core.filter.authentication.sign.AwsSigner;

public class CommonApplication extends Application
{
    /**
     * 注册方式1
     */
    @Override
    public Set<Object> getSingletons()
    {
        Set<Object> objects = new HashSet<Object>();

        // 注册provider
        objects.add(new JacksonJsonProvider());

        // 注册过滤器
        objects.add(new AwsServerAuthenticationFilter(new AwsSigner()));

        // 注册exceptionMapper
        objects.add(new JsonMappingExceptionMapper());
        objects.add(new JsonParseExceptionMapper());
        objects.add(new RESTServiceExceptionMapper());

        return objects;
    }

    /**
     * 注册方式2
     */
    @Override
    public Set<Class<?>> getClasses()
    {
        HashSet<Class<?>> clazzes = new HashSet<Class<?>>();

        // // 注册provider
        // clazzes.add(JacksonJsonProvider.class);
        //
        // // 注册过滤器
        // // clazzes.add(new AwsServerAuthenticationFilter(new AwsSigner())); // 这种好像支持不了
        //
        // // 注册exceptionMapper
        // clazzes.add(JsonMappingExceptionMapper.class);
        // clazzes.add(JsonParseExceptionMapper.class);
        // clazzes.add(RESTServiceExceptionMapper.class);

        return clazzes;
    }
}
