package com.springinaction.springidol;

import java.util.Iterator;
import java.util.Properties;

public class OneManBand implements Performer {
  public OneManBand() {}
  
  public void perform() throws PerformanceException {
    for (Iterator iter = instruments.keySet().iterator(); 
        iter.hasNext();) {
      String key = (String) iter.next();
      System.out.println(key + " : " + instruments.getProperty(key));
    }
  }  
  
  private Properties instruments;
  public void setInstruments(Properties instruments) {
    this.instruments = instruments;
  }
}
