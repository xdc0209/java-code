package com.springinaction.springidol;

public class Magician implements Performer {
  public Magician() {}
  
  public void perform() throws PerformanceException {
    System.out.println(magicWords);
    System.out.println("The magic box contains...");
    System.out.println(magicBox.getContents());
  }
  
  // injected
  private MagicBox magicBox;
  public void setMagicBox(MagicBox magicBox) {
    this.magicBox = magicBox;
  }
  
  private String magicWords;
  public void setMagicWords(String magicWords) {
    this.magicWords = magicWords;
  }
}
