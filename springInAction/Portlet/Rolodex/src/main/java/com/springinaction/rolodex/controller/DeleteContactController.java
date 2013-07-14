package com.springinaction.rolodex.controller;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.portlet.ModelAndView;

import com.springinaction.rolodex.service.RolodexService;


public class DeleteContactController extends AbstractController {
  protected void handleActionRequestInternal(ActionRequest request, ActionResponse response)
      throws Exception {
    
    int contactId = Integer.parseInt(request.getParameter("contactId"));
    rolodexService.deleteContact(contactId);
  }
  
  protected ModelAndView handleRenderRequestInternal(RenderRequest request,
      RenderResponse response) throws Exception {
    
    String userName = ControllerUtil.getUserName(request);
    
    List contacts = rolodexService.getContacts(userName);
    
    return new ModelAndView("contactList", "contacts", contacts);  

  }
  
  private RolodexService rolodexService;
  public void setRolodexService(RolodexService rolodexService) {
    this.rolodexService = rolodexService;
  }
}
