package com.springinaction.rolodex.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.springinaction.rolodex.controller.SearchCommand;
import com.springinaction.rolodex.dao.RolodexDao;
import com.springinaction.rolodex.domain.Contact;


public class RolodexServiceImpl implements RolodexService {
  
  public RolodexServiceImpl() {}
  
  public List getContacts(String userName) {
    if(StringUtils.isNotBlank(userName)) {
      return rolodexDao.findContactsForUser(userName);
    }
    
    return rolodexDao.findPublicContacts();
  }
  
  public void addContact(Contact contact, String userName) {
    contact.setOwnerName(userName);
    rolodexDao.saveContact(contact);
  }
  
  public Contact getContact(int id) {
    return rolodexDao.getContact(new Integer(id));
  }
  
  public void deleteContact(int id) {
    Contact contact = getContact(id);
    rolodexDao.deleteContact(contact);
  }
  
  public List searchContacts(String userName, 
      SearchCommand searchCommand) {
    
    // TODO: Tweak rolodexDao to support first
    //       and last name searching. Use last
    //       name for now.
    
    if(StringUtils.isNotBlank(userName)) {
      return rolodexDao.searchContactsForUser(userName, 
          searchCommand.getLastName());
    }
    
    return rolodexDao.searchPublicContacts(
        searchCommand.getLastName());
  }
  
  private RolodexDao rolodexDao;
  public void setRolodexDao(RolodexDao rolodexDao) {
    this.rolodexDao = rolodexDao;
  }
}
