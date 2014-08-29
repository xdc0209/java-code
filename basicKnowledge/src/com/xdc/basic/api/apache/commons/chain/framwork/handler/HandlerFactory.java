package com.xdc.basic.api.apache.commons.chain.framwork.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.handler.cluser.CreateClusterHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.handler.cluser.DeleteClusterHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.handler.instance.CreateInstanceHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.handler.instance.CustomizeInstanceHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.handler.instance.DeleteInstanceHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.message.cluser.CreateClusterRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.message.cluser.DeleteClusterRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.CreateInstanceRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.CustomizeInstanceRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.DeleteInstanceRequest;

public class HandlerFactory
{
    private static Log                                           log = LogFactory.getLog(HandlerFactory.class);

    private static Map<Class<?>, List<Class<? extends Command>>> map = new HashMap<Class<?>, List<Class<? extends Command>>>();

    static
    {
        try
        {
            registerHander(CreateClusterRequest.class, CreateClusterHandler.class);
            registerHander(DeleteClusterRequest.class, DeleteClusterHandler.class);

            registerHander(CreateInstanceRequest.class, CreateInstanceHandler.class, CustomizeInstanceHandler.class);
            registerHander(CustomizeInstanceRequest.class, CustomizeInstanceHandler.class);
            registerHander(DeleteInstanceRequest.class, DeleteInstanceHandler.class);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    private static void registerHander(Class<?> requestClazz, Class<? extends Command> handlerClazz,
            Class<?>... remainHandlerClazzes)
    {

        ArrayList<Class<?>> HandlerClazzes = new ArrayList<Class<?>>();
        HandlerClazzes.add(handlerClazz);
        for (Class<?> clazz : remainHandlerClazzes)
        {
            HandlerClazzes.add(clazz);
        }

        // map.put(requestClazz, HandlerClazzes);
    }

    public static List<Command> getHanders(Class<?> requestClazz)
    {
        List<Command> commands = new ArrayList<Command>();

        List<Class<? extends Command>> HandlerClazzes = map.get(requestClazz);
        if (CollectionUtils.isEmpty(HandlerClazzes))
        {
            log.error(String.format("Handler for [%s] not found.", requestClazz.getName()));
            return commands;
        }

        for (Class<? extends Command> clazz : HandlerClazzes)
        {
            try
            {
                commands.add(clazz.newInstance());
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                log.error("Cteate Handler fail.", e);
            }
        }

        return commands;
    }
}
