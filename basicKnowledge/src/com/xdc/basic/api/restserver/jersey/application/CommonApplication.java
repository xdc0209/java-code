package com.xdc.basic.api.restserver.jersey.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common.IdNotUniqueMapper;
import com.xdc.basic.api.restserver.jersey.application.exceptionmapper.common.ResourceNotExistMapper;

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

        // 注册exceptionMapper
        objects.add(new ResourceNotExistMapper());
        objects.add(new IdNotUniqueMapper());

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
        // // 注册exceptionMapper
        // clazzes.add(ResourceNotExistMapper.class);
        // clazzes.add(IdNotUniqueMapper.class);

        return clazzes;
    }
}
