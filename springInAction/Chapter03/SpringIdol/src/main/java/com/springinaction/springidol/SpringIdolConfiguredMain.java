package com.springinaction.springidol;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIdolConfiguredMain
{
    public static void main(String[] args) throws Exception
    {
        new ClassPathXmlApplicationContext("com/springinaction/springidol/spring-idol-configured.xml");

        Instrumentalist pianist = new Instrumentalist();
        pianist.perform();
    }
}
