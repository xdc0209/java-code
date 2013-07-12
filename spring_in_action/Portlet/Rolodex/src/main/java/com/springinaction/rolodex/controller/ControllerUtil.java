package com.springinaction.rolodex.controller;

import java.security.Principal;

import javax.portlet.PortletRequest;


public class ControllerUtil {  
  public static String getUserName(
      PortletRequest request) {
    Principal userPrincipal = 
      request.getUserPrincipal();

    return (userPrincipal == null) ? 
           null : 
           userPrincipal.getName();
  }
}
