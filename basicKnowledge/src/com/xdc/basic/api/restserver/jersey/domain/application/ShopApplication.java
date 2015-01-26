package com.xdc.basic.api.restserver.jersey.domain.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.xdc.basic.api.restserver.jersey.domain.application.resource.shop.DemoResource;

public class ShopApplication extends Application
{
    @Override
    public Set<Object> getSingletons()
    {
        Set<Object> objects = new HashSet<Object>();

        // 注册resource
        objects.add(new DemoResource());

        return objects;
    }
}
