package com.springinaction.chapter01.knight;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;


public class MinstrelAdvice implements MethodBeforeAdvice {
  public void before(Method method, Object[] args, Object target) 
      throws Throwable {

    Knight knight = (Knight) target;
    
    Logger song = Logger.getLogger(target.getClass());
    
    song.debug("Brave " + knight.getName() + " did " + method.getName());
  }
}
