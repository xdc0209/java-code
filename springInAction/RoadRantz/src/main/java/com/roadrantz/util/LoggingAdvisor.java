package com.roadrantz.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class LoggingAdvisor {
  private static final Logger LOGGER = 
    Logger.getLogger(LoggingAdvisor.class);

  public Object log(ProceedingJoinPoint pjp) throws Throwable {
    LOGGER.error("**********************************************");
    LOGGER.error("LOGGING BEFORE:  " + pjp.getSignature().toLongString());
    LOGGER.error("**********************************************");
    
    Object result = pjp.proceed();
    LOGGER.error("**********************************************");
    LOGGER.error("LOGGING AFTER:  " + pjp.getSignature().toLongString() + ", RETURNING:  " + result);
    LOGGER.error("**********************************************");
    
    return result;
  }
}
