package com.springinaction.rolodex.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;


public class PreferencesController extends SimpleFormController {
  public PreferencesController() {
    setCommandClass(PreferencesCommand.class);
  }
  
  protected Object formBackingObject(PortletRequest request) throws Exception {
    PreferencesCommand command = (PreferencesCommand) createCommand();
    PortletPreferences preferences = request.getPreferences();
    command.setPageSize(Integer.parseInt(
        preferences.getValue("pageSize", PreferencesCommand.DEFAULT_PAGE_SIZE)));

    return command;
  }

  protected void onSubmitAction(ActionRequest request, ActionResponse response,
      Object command, BindException bindException) throws Exception {
    
    PreferencesCommand prefCommand = (PreferencesCommand) command;
    PortletPreferences preferences = request.getPreferences();
    preferences.setValue("pageSize", prefCommand.getPageSize()+"");
    preferences.store();
  }
}
