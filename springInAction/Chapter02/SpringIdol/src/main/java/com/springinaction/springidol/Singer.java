package com.springinaction.springidol;

public class Singer implements Performer {
  private String name = "Someone";
  private Song song;
  
  public Singer() {}
  
  public Singer(Song song) {
    this.song = song;
  }
  
  public Singer(String name, Song song) {
    this.song = song;
    this.name = name;
  }
  
  public void perform() throws PerformanceException {
    System.out.println(name + " IS SINGING " + song.getTitle());
  }  
}
