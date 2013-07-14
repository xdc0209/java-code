package com.springinaction.hello;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class HelloPortlet2 extends GenericPortlet {
  protected void doView(RenderRequest request, 
      RenderResponse response) 
      throws PortletException, IOException {

    String message = "<h2>Hello world</h2>";   
    response.getWriter().write(message);
  }
}
