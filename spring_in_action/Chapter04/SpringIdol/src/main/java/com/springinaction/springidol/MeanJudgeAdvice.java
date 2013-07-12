package com.springinaction.springidol;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class MeanJudgeAdvice implements MethodBeforeAdvice, 
    AfterReturningAdvice, ThrowsAdvice {
  public MeanJudgeAdvice() {}
  
  public void before(Method method, Object[] args, 
      Object target) throws Throwable {
    
    System.out.println("BEFORE PERFORMANCE");    
  }

  public void afterReturning(Object returnValue, 
      Method method, Object[] args, Object target) 
      throws Throwable {
    
    System.out.println("AFTER PERFORMANCE");
  }
  
  public void afterThrowing(PerformanceException e) {
    System.out.println("AFTER EXCEPTION");
  }
}
