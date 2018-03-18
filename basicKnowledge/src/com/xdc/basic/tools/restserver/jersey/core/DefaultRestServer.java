package com.xdc.basic.tools.restserver.jersey.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.xdc.basic.tools.restserver.jersey.core.api.RestServer;
import com.xdc.basic.tools.restserver.jersey.core.api.ServerException;
import com.xdc.basic.tools.restserver.jersey.core.config.RestServerConfig;
import com.xdc.basic.tools.restserver.jersey.utils.ApplicationUtils;

public class DefaultRestServer implements RestServer
{
    private Server                                  server             = null;

    private final Set<Class<? extends Application>> applicationClazzes = new HashSet<Class<? extends Application>>();

    @Override
    public synchronized void start() throws ServerException
    {
        if (server != null && server.isRunning())
        {
            System.err.println("Server is running, do nothing.");
            return;
        }

        ResourceConfig resourceConfig = createResourceConfig(applicationClazzes);
        server = createServer(resourceConfig);

        try
        {
            server.start();
            // server.join();
        }
        catch (Exception e)
        {
            throw new ServerException(e);
        }
    }

    @Override
    public synchronized void stop() throws ServerException
    {
        if (server != null && !server.isRunning())
        {
            System.err.println("Server is stopped, do nothing.");
            return;
        }

        try
        {
            server.stop();
            server.join();
            server.destroy();
            server = null;
        }
        catch (Exception e)
        {
            throw new ServerException(e);
        }
    }

    @Override
    public synchronized void bindApplication(Class<? extends Application> applicationClazz) throws ServerException
    {
        if (null == applicationClazz)
        {
            System.out.println("Application is null, ignore it.");
            return;
        }

        applicationClazzes.add(applicationClazz);

        // 如果server已经启动，需要重启，保证生效
        if (server != null && server.isRunning())
        {
            stop();
            start();
        }
    }

    @Override
    public synchronized void unbindApplication(Class<? extends Application> applicationClazz) throws ServerException
    {
        if (null == applicationClazz)
        {
            System.out.println("Application is null, ignore it.");
            return;
        }

        applicationClazzes.remove(applicationClazz);

        // 如果server已经启动，需要重启，保证生效
        if (server != null && server.isRunning())
        {
            stop();
            start();
        }
    }

    /**
     * 创建资源配置
     * 
     * @param applications
     */
    private ResourceConfig createResourceConfig(Set<Class<? extends Application>> applicationClazzes)
    {
        /**
         * RS Application包装器，用于集成多个RS Application实现。
         * 
         * Jersey不支持一个Servlet注册多个Application， 此类实现将多个Application包装成一个Application，以达到注册同时多个Application的目的
         */
        class ApplicationWrapper extends Application
        {
            Set<Class<? extends Application>> applicationClazzes = new HashSet<Class<? extends Application>>();

            public ApplicationWrapper(Set<Class<? extends Application>> applicationClazzes)
            {
                this.applicationClazzes.addAll(applicationClazzes);
            }

            @Override
            public Set<Class<?>> getClasses()
            {
                Set<Class<?>> list = new HashSet<Class<?>>();
                for (Class<? extends Application> applicationClazz : applicationClazzes)
                {
                    try
                    {
                        Application application = applicationClazz.newInstance();
                        list.addAll(application.getClasses());
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
                return list;
            }

            @Override
            public Set<Object> getSingletons()
            {
                Set<Object> list = new HashSet<Object>();
                for (Class<? extends Application> applicationClazz : applicationClazzes)
                {
                    try
                    {
                        Application application = applicationClazz.newInstance();
                        list.addAll(application.getSingletons());
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
                return list;
            }
        }

        ApplicationWrapper application = new ApplicationWrapper(applicationClazzes);

        List<String> paths = ApplicationUtils.getPaths(application);
        System.out.println("Paths:");
        for (String path : paths)
        {
            System.out.println(path);
        }
        System.out.println();

        return ResourceConfig.forApplication(application);
    }

    /**
     * 创建http服务端
     */
    private Server createServer(ResourceConfig resourceConfig)
    {
        Server server = new Server();

        // 设置在JVM退出时关闭Jetty的钩子。
        server.setStopAtShutdown(true);

        ServerConnector connector = new ServerConnector(server);
        {
            connector.setIdleTimeout(1000 * 60 * 10);
            connector.setSoLingerTime(-1);

            // 解决Windows下重复启动Jetty居然不报告端口冲突的问题。
            connector.setReuseAddress(false);
            connector.setHost(RestServerConfig.getRestServerIp());
            connector.setPort(RestServerConfig.getRestServerPort());
        }
        server.setConnectors(new Connector[] { connector });

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        {
            ServletContainer servletContainer = new ServletContainer(resourceConfig);
            ServletHolder servletHolder = new ServletHolder(servletContainer);

            context.setContextPath("/");
            context.addServlet(servletHolder, "/*");
        }
        server.setHandler(context);

        return server;
    }
}
