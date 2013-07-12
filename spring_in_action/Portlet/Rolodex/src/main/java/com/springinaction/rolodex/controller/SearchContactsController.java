package com.springinaction.rolodex.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractCommandController;

import com.springinaction.rolodex.service.RolodexService;

public class SearchContactsController 
    extends AbstractCommandController {
  
  public SearchContactsController() {
    setCommandClass(SearchCommand.class);
  }
  
  protected void handleAction(
      ActionRequest request, 
      ActionResponse response, 
      Object command, 
      BindException bindException) 
      throws Exception {
  }

  protected ModelAndView handleRender(
      RenderRequest request, 
      RenderResponse response, 
      Object command, 
      BindException bindException) 
      throws Exception {
    
    String userName = 
        ControllerUtil.getUserName(request);
    
    SearchCommand searchCommand = 
        (SearchCommand) command;
    
    List contacts = rolodexService.
        searchContacts(userName, 
            searchCommand);
    
    Map model = new HashMap();
    model.put("contacts", contacts);
    model.put("actionName", "searchContacts");
    model.put("pageSize", 
        request.getPreferences().getValue(
            "pageSize", "5"));

    return new ModelAndView(
        "searchResults", model);  
  }
  
  private RolodexService rolodexService;
  public void setRolodexService(
      RolodexService rolodexService) {
    this.rolodexService = rolodexService;
  }
}
