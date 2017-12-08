package com.roadrantz.tapestry;

import java.util.List;

import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.html.BasePage;

import com.roadrantz.domain.Rant;
import com.roadrantz.service.RantService;

public abstract class HomePage extends BasePage {
  @InjectObject("spring:rantService")
  public abstract RantService getRantService();
  
  public abstract Rant getRant();
  public abstract void setRant(Rant rant);

  public List getRants() {
    return getRantService().getRecentRants();
  }
}
