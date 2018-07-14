package com.springinaction.rolodex.controller;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.portlet.ModelAndView;

import com.springinaction.rolodex.domain.Contact;
import com.springinaction.rolodex.service.RolodexService;


public class ContactDetailController extends AbstractController {
  
  protected ModelAndView handleRenderRequestInternal(RenderRequest request,
      RenderResponse response) throws Exception {
    int id = Integer.parseInt(request.getParameter("contactId"));
    Contact contact = rolodexService.getContact(id);
    
    return new ModelAndView("contactDetail", "contact", contact);
  }
  
  private RolodexService rolodexService;
  public void setRolodexService(RolodexService rolodexService) {
    this.rolodexService = rolodexService;
  }
}
