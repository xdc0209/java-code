package com.xdc.basic.api.restserver.jersey;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.util.annotation.ManagedObject;

public class ClientMain
{
    public static void main(String[] args)
    {
        Client client = ClientBuilder.newClient();
        //client.register(MIMJsonProvider.class);

        WebTarget target = client.target("http://127.0.0.1:8080");

        try
        {
            getMO(target, "Host=I2000-Server");

            //            ManagedObject host = new Host("G36811");
            //            host.setDN("Host=G36811");
            //            host.setType("Blade2588");
            //            List<AgentNode> list = new ArrayList<AgentNode>();
            //
            //            asyncCallback(target, DN.gen("abc"), host, list);

            syncTask(target);
        }
        catch (ProcessingException e)
        {
            if (e.getCause() instanceof ConnectException)
            {
                System.err.println("无法访问目标服务");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected static void syncTask(WebTarget target)
    {
        //        EventInput eventInput = target.path("i2000/am/task").request().post(Entity.text("MyTask"), EventInput.class);
        //        while (!eventInput.isClosed())
        //        {
        //            final InboundEvent event = eventInput.read();
        //            if (event == null)
        //            {
        //                // connection has been closed
        //                break;
        //            }
        //            System.out.println(event.getName() + "; " + event.readData(String.class));
        //        }
    }

    protected static void getMO(WebTarget target, String dn)
    {
        Builder builder = target.path("i2000/mit/{dn}").resolveTemplate("dn", dn)
                .request(MediaType.APPLICATION_JSON_TYPE).header("Date", System.currentTimeMillis());
        Response response = builder.get();
        ManagedObject mo = null;
        if (response.getStatus() == 200)
        {
            mo = response.readEntity(ManagedObject.class);
        }
        else
        {
            System.out.println("return code: " + response.getStatus() + "; reason: "
                    + response.readEntity(String.class));
        }
        if (null != mo)
        {
            System.out.println("get result: " + mo);
        }
        System.out.println();
    }

    // protected static void asyncCallback(WebTarget target, DN parent, ManagedObject mainObj, List<AgentNode> agents)
    protected static void asyncCallback(WebTarget target, Object parent, ManagedObject mainObj, List<Object> agents)
    {
        Builder builder = target.path("i2000/am/{parent}").resolveTemplate("parent", parent)
                .request(MediaType.APPLICATION_JSON_TYPE).header("Date", System.currentTimeMillis());

        //  List<ManagedObject> list = new ArrayList<ManagedObject>();
        List<Object> list = new ArrayList<Object>();
        list.add(mainObj);
        list.addAll(agents);
        final AsyncInvoker asyncInvoker = builder.async();
        asyncInvoker.put(Entity.json(list), new InvocationCallback<Response>()
        {
            @Override
            public void completed(Response response)
            {
                if (200 == response.getStatus())
                {
                    // System.out.println("get result: " + response.readEntity(DN.class));
                    System.out.println("get result: " + response.readEntity(Object.class));
                }
                else
                {
                    System.out.println("return code: " + response.getStatus() + "; reason: "
                            + response.readEntity(String.class));
                }
                response.close();
            }

            @Override
            public void failed(Throwable t)
            {
                t.printStackTrace();
            }
        });

    }
}