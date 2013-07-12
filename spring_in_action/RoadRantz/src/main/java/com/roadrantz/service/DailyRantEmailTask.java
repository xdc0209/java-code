package com.roadrantz.service;

import java.util.TimerTask;

/**
 * Java timer task to call the RantService's sendDailyRantEmails.
 *
 * As shown in Listing 12.3, page 458.
 *
 * @author craig.walls
 */
public class DailyRantEmailTask extends TimerTask {
  public DailyRantEmailTask() {}

  public void run() {
    rantService.sendDailyRantEmails();
  }

  // injected
  private RantService rantService;
  public void setRantService(RantService rantService) {
    this.rantService = rantService;
  }
}
