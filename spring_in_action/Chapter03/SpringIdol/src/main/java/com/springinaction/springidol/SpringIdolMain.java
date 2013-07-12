package com.springinaction.springidol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIdolMain {
  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "com/springinaction/springidol/spring-idol.xml");
    
    TalentCompetition competition = 
        (TalentCompetition) ctx.getBean("springIdol");
    
    competition.run();
  }
  
}
