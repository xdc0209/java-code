package com.springinaction.chapter03.postprocessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FuddMain {
  public static void main(String[] args) throws Exception {
    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext(
            "com/springinaction/chapter03/postprocessor/fudd.xml");
    
    Rabbit bugs = (Rabbit) ctx.getBean("bugs");
    
    System.out.println(bugs.getDescription());
  }
}
