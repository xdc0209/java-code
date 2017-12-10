package com.springinaction.rolodex.dao;

import java.util.List;

import com.springinaction.rolodex.domain.Contact;

public interface RolodexDao {
  public Contact getContact(Integer contactId);

  public List findContactsForUser(String userName);
  public List findPublicContacts();
  
  public void saveContact(Contact contact);
  
  public void deleteContact(Contact contact);
  
  public List searchPublicContacts(String searchText);
  public List searchContactsForUser(String userName, String searchText);
}