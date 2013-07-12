package com.springinaction.springidol;

public aspect JudgeAspect {
  public JudgeAspect() {}
  
  pointcut performance() : execution(* perform(..));
  
  after() returning() : performance() {
    System.out.println(criticismEngine.getCriticism());
  }
  
  // injected
  private CriticismEngine criticismEngine;

  public void setCriticismEngine(CriticismEngine criticismEngine) {
    this.criticismEngine = criticismEngine;
  }
}
