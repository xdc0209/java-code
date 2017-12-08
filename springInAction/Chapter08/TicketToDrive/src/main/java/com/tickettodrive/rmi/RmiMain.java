package com.tickettodrive.rmi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiMain {
   public static void main(String[] args) {
      new ClassPathXmlApplicationContext("ttd-rmi-service.xml");
   }
}
