package com.springinaction.chapter01.knight;


public interface Quest {
  public Object embark() throws QuestFailedException;
}
