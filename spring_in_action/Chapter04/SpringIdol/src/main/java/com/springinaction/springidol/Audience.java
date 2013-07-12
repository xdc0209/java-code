package com.springinaction.springidol;

import org.aspectj.lang.ProceedingJoinPoint;

public class Audience {
  public Audience() {}
  
  public void watchPerformance(ProceedingJoinPoint jp) {
    System.out.println("The audience is taking their seats.");
    System.out.println("The audience is turning off their cellphones");
    try {
      jp.proceed();
      System.out.println("CLAP CLAP CLAP CLAP CLAP");
    } catch (Throwable throwable) {
      System.out.println("Boo! We want our money back!");      
    }
  }  
  
  public void takeSeats() {
    System.out.println("The audience is taking their seats.");
  }
  
  public void turnOffCellPhones() {
    System.out.println("The audience is turning off their cellphones");
  }
  
  public void applaud() {
    System.out.println("CLAP CLAP CLAP CLAP CLAP");
  }
  
  public void demandRefund() {
    System.out.println("Boo! We want our money back!");
  }
}
