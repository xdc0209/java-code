package com.roadrantz.service.mbean;

import javax.management.Notification;
import javax.management.NotificationListener;

public class MyNotificationListener implements NotificationListener {
  public MyNotificationListener() {}
  
  public void handleNotification(Notification notification, 
      Object handback) {
     // do something
  }
}
