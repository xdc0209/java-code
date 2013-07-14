package com.springinaction.springidol;

public abstract class MethodInjectedInstrumentalist 
    implements Performer {
  public MethodInjectedInstrumentalist() {}
  
  public void perform() throws PerformanceException {
    System.out.println("Playing " + song + " : " );
    getInstrument().play();
  }
  
  private String song;
  public void setSong(String song) {
    this.song = song;
  }
  
  public abstract Instrument getInstrument();
}
