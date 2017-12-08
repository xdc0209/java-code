package com.springinaction.rolodex.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.springinaction.rolodex.domain.Contact;


public class RolodexDaoHibernate extends HibernateDaoSupport implements RolodexDao {
  private static final String BASE_CONTACTS_QUERY =
      "from " + Contact.class.getName() + " c ";
  
  private static final String PUBLIC_CONTACTS_QUERY = 
      BASE_CONTACTS_QUERY + 
      "where c.ownerName is null";
  
  private static final String USER_CONTACTS_QUERY =
      PUBLIC_CONTACTS_QUERY + " or c.ownerName = ?";
  
  private static final String SEARCH_PUBLIC_CONTACTS_QUERY =
      BASE_CONTACTS_QUERY +
      " where c.ownerName is null " +
      " and c.lastName = ?";

  private static final String SEARCH_USER_CONTACTS_QUERY =
      BASE_CONTACTS_QUERY +
      " where (c.ownerName is null or c.ownerName = ?) " +
      " and c.lastName = ?";
  
  public RolodexDaoHibernate() {}
  
  public Contact getContact(Integer contactId) {
    return (Contact) getHibernateTemplate().get(Contact.class, contactId);
  }
  
  public List findContactsForUser(String userName) {
    return getHibernateTemplate().find(USER_CONTACTS_QUERY, userName);
  }
  
  public List findPublicContacts() {
    return getHibernateTemplate().find(PUBLIC_CONTACTS_QUERY);
  }
  
  public void saveContact(Contact contact) {
    getHibernateTemplate().saveOrUpdate(contact);
  }
  
  public void deleteContact(Contact contact) {
    getHibernateTemplate().delete(contact);
  }

  public List searchPublicContacts(String searchText) {
    return getHibernateTemplate().find(SEARCH_PUBLIC_CONTACTS_QUERY, 
        new Object[]{searchText});
  }

  public List searchContactsForUser(String userName, String searchText) {
    return getHibernateTemplate().find(SEARCH_USER_CONTACTS_QUERY, 
        new Object[]{userName, searchText});
  }
}
