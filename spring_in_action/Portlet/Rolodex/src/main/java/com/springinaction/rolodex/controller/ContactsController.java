package com.springinaction.rolodex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

import com.springinaction.rolodex.service.RolodexService;

public class ContactsController 
    extends AbstractController {    

  protected 
      ModelAndView handleRenderRequestInternal(
          RenderRequest request, 
          RenderResponse response) 
          throws Exception {    

    String userName = 
        ControllerUtil.getUserName(request);
    List contacts = 
        rolodexService.getContacts(userName);

    Map model = new HashMap();    
    model.put("contacts", contacts);
    model.put("actionName", "contactList");
    model.put("pageSize", 
        request.getPreferences().getValue(
            "pageSize", 
            PreferencesCommand.DEFAULT_PAGE_SIZE));

    return new ModelAndView("contactList", model);  
  }
  
  private RolodexService rolodexService;
  public void setRolodexService(
      RolodexService rolodexService) {
    this.rolodexService = rolodexService;
  }
}
