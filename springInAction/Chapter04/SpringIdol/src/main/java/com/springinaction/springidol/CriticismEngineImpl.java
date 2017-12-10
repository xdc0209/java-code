package com.springinaction.springidol;

public class CriticismEngineImpl implements CriticismEngine {
  public CriticismEngineImpl() {}
  
  public String getCriticism() {
    int i = (int) (Math.random() * criticismPool.length);
    
    return criticismPool[i];
  }
  
  // injected
  private String[] criticismPool;
  public void setCriticismPool(String[] criticismPool) {
    this.criticismPool = criticismPool;
  }
}
