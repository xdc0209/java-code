package com.springinaction.rolodex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.web.portlet.ModelAndView;

import com.springinaction.rolodex.controller.ContactsController;
import com.springinaction.rolodex.domain.Contact;
import com.springinaction.rolodex.service.RolodexService;


public class ContactsControllerTest extends TestCase {
  public ContactsControllerTest() {
    super();
  }
  
  public ContactsControllerTest(String name) {
    super(name);
  }

  private ContactsController controller;

  private MockControl control;
  private RolodexService rolodexService;
  
  protected void setUp() throws Exception {
    
    control = MockControl.createControl(RolodexService.class);
    rolodexService = (RolodexService) control.getMock();
    rolodexService.getContacts(null);
    List contactList = new ArrayList();
    Contact contact = new Contact();
    contactList.add(contact);
    control.expectAndReturn(null, contactList);
    control.replay();
    
    controller = new ContactsController();
    controller.setRolodexService(rolodexService);
  }
  
  public void testNoAuthRequest() {
    MockRenderRequest request = new MockRenderRequest();
    MockRenderResponse response = new MockRenderResponse();
    
    try {
      ModelAndView mv = controller.handleRenderRequestInternal(request, response);

      assertEquals("contactList", mv.getViewName());
      
      Map model = mv.getModel();
      assertNotNull(model);
      
      List contacts = (List) model.get("contacts");
      assertEquals(1, contacts.size());
    } catch (Exception e) {
      fail("Exception thrown from ContactsController.handleRenderRequestInternal() : " + e);
    }
  }
}
