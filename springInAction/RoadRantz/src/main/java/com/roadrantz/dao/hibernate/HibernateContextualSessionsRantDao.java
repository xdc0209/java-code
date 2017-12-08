package com.roadrantz.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * Hibernate contextual session based DAO.
 * 
 * Although the name is different, this is the Hibernate-based DAO that is
 * described in section 5.4.4.
 * 
 * Note that since chapter 5 was written, I have learned that this is the
 * preferred approach to building Hibernate DAOs.
 * 
 * @author wallsc
 */
public class HibernateContextualSessionsRantDao implements RantDao {
   private static final String MOTORIST = Motorist.class.getName();
   private static final String RANT = Rant.class.getName();
   private static final String VEHICLE = Vehicle.class.getName();

   public HibernateContextualSessionsRantDao() {}

   public Vehicle findVehicleByPlate(String state, String plateNumber) {
      List results = sessionFactory
                        .getCurrentSession()
                        .find(
                                          "from "
                                                            + VEHICLE
                                                            + " where state = ? and plateNumber = ?",
                                          new Object[] { state, plateNumber },
                                          new Type[] { Hibernate.STRING,
                                                Hibernate.STRING });

      if (results.size() > 0) {
         return (Vehicle) results.get(0);
      }

      return null; // TODO - Should I throw an exception instead?
   }

   public List<Rant> getAllRants() {
      return sessionFactory.getCurrentSession().find("from " + RANT);
   }

   public Motorist getMotoristByEmail(String email) {
      List results = sessionFactory.getCurrentSession()
                        .find("from " + MOTORIST + " where email = ?",
                                          new Object[] { email },
                                          new Type[] { Hibernate.STRING });

      if (results.size() > 0) {
         return (Motorist) results.get(0);
      }
      return null; // TODO - Should I throw an exception instead?
   }

   public void saveRant(Rant rant) {
      sessionFactory.getCurrentSession().saveOrUpdate(rant);
   }

   public void saveVehicle(Vehicle vehicle) {
      sessionFactory.getCurrentSession().saveOrUpdate(vehicle);
   }

   public List<Rant> getRantsForDay(Date day) {
      return sessionFactory.getCurrentSession().find(
                        "from " + RANT + " where postedDate = ?", day,
                        Hibernate.DATE);
   }

   public void saveMotorist(Motorist driver) {
      sessionFactory.getCurrentSession().saveOrUpdate(driver);
   }

   public Motorist getDriverById(Integer id) {
      return (Motorist) sessionFactory.getCurrentSession().load(Motorist.class,
                        id);
   }

   public int getMotoristCount() {
      // TODO Auto-generated method stub
      return 0;
   }

   private SessionFactory sessionFactory;

   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }
}
