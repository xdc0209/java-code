package com.springinaction.springidol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIdolAop {
  public static void main(String[] args) {
    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("com/springinaction/springidol/spring-idol.xml");
    
    Performer performer = (Performer) ctx.getBean("bo");
    performer.perform();
    System.out.println("-----------------------------");
    Performer performer2 = (Performer) ctx.getBean("william");
    performer2.perform();
  }
}
