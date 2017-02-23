package com.springinaction.chapter01.knight;


public class HolyGrailQuest implements Quest {
  public HolyGrailQuest() {}
  
  public Object embark() throws GrailNotFoundException {
    // do whatever it means to embark on a quest
    return new HolyGrail();
  }
}
