package com.xdc.basic.api.restserver.jersey.core.api;

import javax.ws.rs.core.Application;

public interface RestServer
{
    void start() throws ServerException;

    void stop() throws ServerException;

    void bindApplication(Application application) throws ServerException;

    void unbindApplication(Application application) throws ServerException;
}
