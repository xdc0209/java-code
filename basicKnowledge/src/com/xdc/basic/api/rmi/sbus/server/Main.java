package com.xdc.basic.api.rmi.sbus.server;

import java.io.IOException;

import com.xdc.basic.api.rmi.sbus.test.impl.CourseServiceImpl;
import com.xdc.basic.api.rmi.sbus.test.impl.StudentServiceImpl;
import com.xdc.basic.api.rmi.sbus.test.intf.CourseService;
import com.xdc.basic.api.rmi.sbus.test.intf.StudentService;
import com.xdc.basic.api.rmi.sbus.transport.socket.SocketServer;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        SbusSkeletonManager.registerSbusSkeleton(StudentService.class, new StudentServiceImpl());
        SbusSkeletonManager.registerSbusSkeleton(CourseService.class, new CourseServiceImpl());

        SocketServer socketServer = new SocketServer("127.0.0.1", 1989);
        socketServer.registerListener(new SbusServer());
        socketServer.start();

        Thread.sleep(6000000);
        socketServer.stop();
    }
}
