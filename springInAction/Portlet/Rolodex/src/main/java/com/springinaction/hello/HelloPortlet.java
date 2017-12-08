package com.springinaction.hello;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class HelloPortlet implements Portlet {
  public void init(PortletConfig config) 
      throws PortletException {}
  
  public void destroy() {}
  
  public void processAction(ActionRequest request, 
      ActionResponse response) 
      throws PortletException, IOException {
  }
  
  public void render(RenderRequest request, 
      RenderResponse response) 
      throws PortletException, IOException {
    
    String message = "<h2>Hello world</h2>";   
    response.getWriter().write(message);
  }
}
