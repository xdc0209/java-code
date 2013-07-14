package com.springinaction.scripting;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScriptingMain {
  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "com/springinaction/scripting/ctx.xml");
    
    ICoconut coconut = (ICoconut) ctx.getBean("coconut");
    coconut.drinkThemBothUp();
  }
}
