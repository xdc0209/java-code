package com.xdc.basic.api.restserver.jersey;

import java.io.IOException;

import com.xdc.basic.api.restserver.jersey.application.AsyncdemoApplication;
import com.xdc.basic.api.restserver.jersey.application.CommonApplication;
import com.xdc.basic.api.restserver.jersey.application.SchoolApplication;
import com.xdc.basic.api.restserver.jersey.core.DefaultRestServer;
import com.xdc.basic.api.restserver.jersey.core.api.RestServer;
import com.xdc.basic.api.restserver.jersey.core.api.ServerException;

public class ServerMain
{
    public static void main(String[] args)
    {
        try
        {
            RestServer restServer = new DefaultRestServer();

            restServer.bindApplication(new CommonApplication());
            restServer.bindApplication(new AsyncdemoApplication());
            restServer.bindApplication(new SchoolApplication());

            restServer.start();
            System.out.println("Server is strated.");

            System.out.println("Hit enter to stop it...");
            System.in.read();

            restServer.stop();
            System.out.println("Server is stopped.");
        }
        catch (ServerException | IOException e)
        {
            e.printStackTrace();
        }
    }
}