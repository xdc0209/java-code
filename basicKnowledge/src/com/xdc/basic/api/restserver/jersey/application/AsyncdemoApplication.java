package com.xdc.basic.api.restserver.jersey.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.xdc.basic.api.restserver.jersey.application.resource.asyncdemo.BlockingPostChatResource;
import com.xdc.basic.api.restserver.jersey.application.resource.asyncdemo.FireAndForgetChatResource;
import com.xdc.basic.api.restserver.jersey.application.resource.asyncdemo.SimpleLongRunningResource;

public class AsyncdemoApplication extends Application
{
    @Override
    public Set<Object> getSingletons()
    {
        Set<Object> objects = new HashSet<Object>();

        // 注册resource
        objects.add(new BlockingPostChatResource());
        objects.add(new FireAndForgetChatResource());
        objects.add(new SimpleLongRunningResource());

        return objects;
    }
}
