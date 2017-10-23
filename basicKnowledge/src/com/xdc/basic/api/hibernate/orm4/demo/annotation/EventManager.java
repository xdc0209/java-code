package com.xdc.basic.api.hibernate.orm4.demo.annotation;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

/**
 * TODO 初样 需要优化
 * 
 * @author xdc
 * 
 */
public class EventManager
{
    @SuppressWarnings("rawtypes")
    public static void main(String[] args)
    {
        EventManager mgr = new EventManager();

        mgr.createAndStoreEvent("My Event", new Date());

        List events = mgr.listEvents();
        for (int i = 0; i < events.size(); i++)
        {
            Event theEvent = (Event) events.get(i);
            System.out.println("Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate());
        }

        HibernateUtil.getSessionFactory().close();
    }

    @SuppressWarnings("rawtypes")
    private List listEvents()
    {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }

    private void createAndStoreEvent(String title, Date theDate)
    {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);

        session.save(theEvent);

        session.getTransaction().commit();
    }

}
