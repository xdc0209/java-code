package com.xdc.basic.tools.restserver.jersey;

import com.xdc.basic.commons.PauseUtil;
import com.xdc.basic.tools.restserver.jersey.application.AsyncdemoApplication;
import com.xdc.basic.tools.restserver.jersey.application.CommonApplication;
import com.xdc.basic.tools.restserver.jersey.application.SchoolApplication;
import com.xdc.basic.tools.restserver.jersey.core.DefaultRestServer;
import com.xdc.basic.tools.restserver.jersey.core.api.RestServer;
import com.xdc.basic.tools.restserver.jersey.core.api.ServerException;

public class ServerMain
{
    public static void main(String[] args)
    {
        try
        {
            RestServer restServer = new DefaultRestServer();

            restServer.bindApplication(CommonApplication.class);
            restServer.bindApplication(AsyncdemoApplication.class);
            restServer.bindApplication(SchoolApplication.class);

            restServer.start();
            System.out.println("Server is strated.");
            System.out.println("Try http://127.0.0.1:8080/application.wadl");
            PauseUtil.pressEnterToContinue();

            restServer.unbindApplication(SchoolApplication.class);
            PauseUtil.pressEnterToContinue();

            restServer.bindApplication(SchoolApplication.class);
            PauseUtil.pressEnterToContinue();

            restServer.stop();
            System.out.println("Server is stopped.");
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }
    }
}
