package com.roadrantz.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
  private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class);
  
  @Pointcut("execution(* org.acegisecurity.providers.encoding.Md5PasswordEncoder.*(..))")
  public void Md5Methods() {}
  
  @Around("Md5Methods()")
  public void logIt(ProceedingJoinPoint joinpoint) {
    try {
      LOGGER.error("**********************************");
      LOGGER.error(joinpoint.getSignature().toLongString());
      LOGGER.error("**********************************");
      Object o = joinpoint.proceed();
      LOGGER.error("**********************************");
      LOGGER.error("o:  " + o);
      LOGGER.error("**********************************");
    } catch (Throwable t) {
      LOGGER.error("**********************************");
      LOGGER.error("t:  " + t);
      LOGGER.error("**********************************");      
    }
  }
}
