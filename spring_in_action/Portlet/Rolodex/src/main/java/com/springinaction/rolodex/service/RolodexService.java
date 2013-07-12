package com.springinaction.rolodex.service;

import java.util.List;

import com.springinaction.rolodex.controller.SearchCommand;
import com.springinaction.rolodex.domain.Contact;

public interface RolodexService {
  public List getContacts(String userName);

  public void addContact(Contact contact, String userName);
  
  public Contact getContact(int id);
  
  public void deleteContact(int id);
  
  public List searchContacts(String userName, 
      SearchCommand searchCommand);
}
