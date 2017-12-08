package com.xdc.basic.api.jmx.example.basic;

import javax.management.Notification;
import javax.management.NotificationListener;

public class ClientListener implements NotificationListener
{
    @Override
    public void handleNotification(Notification notification, Object handback)
    {
        System.out.println("Received notification: " + notification);
    }
}
