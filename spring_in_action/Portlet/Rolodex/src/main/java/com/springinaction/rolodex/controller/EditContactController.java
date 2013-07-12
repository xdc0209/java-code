package com.springinaction.rolodex.controller;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.portlet.bind.PortletRequestUtils;
import org.springframework.web.portlet.mvc.SimpleFormController;

import com.springinaction.rolodex.domain.Contact;
import com.springinaction.rolodex.service.RolodexService;

public class EditContactController 
    extends SimpleFormController {
  
  public EditContactController() {
    setCommandClass(Contact.class);
  }
 
  protected Object formBackingObject(
      PortletRequest request) throws Exception {
    
    int contactId = PortletRequestUtils.getIntParameter(
        request, "contactId", -1);
    
    Contact contact = 
        (contactId < 0) ? 
        new Contact() : 
        rolodexService.getContact(contactId);

    if(contact == null) {
      throw new 
          PortletException("Contact not found");
    }
    
    return contact;
  }
  
//  protected ModelAndView processFormSubmission(
//      RenderRequest request,
//      RenderResponse response, Object command, 
//      BindException bindException) 
//      throws Exception {
//    return new ModelAndView(getSuccessView());
//  }
  
  
  protected void onSubmitAction(
      ActionRequest request,
      ActionResponse response, Object command, 
      BindException bindException) 
      throws Exception {

    String userName = 
        ControllerUtil.getUserName(request);
    
    Contact contact = (Contact) command;
    rolodexService.addContact(contact, userName);
    
    response.setRenderParameter(
        "action", "contacts");
  }
  
  // injected
  private RolodexService rolodexService;
  public void setRolodexService(
      RolodexService rolodexService) {
    this.rolodexService = rolodexService;
  }

}
