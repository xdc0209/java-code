package com.roadrantz.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * Hibernate-based implementation of the RantDao interface.
 * 
 * Initially defined in Listing 5.8. But the initial implementation of
 * HibernateRantDao evolves in section 5.4.3 to extend HibernateDaoSupport.
 * 
 * If you're looking for the version from section 5.4.2, have a look at
 * HibernateRantDaoUsingTemplate.java.
 * 
 * @author wallsc
 */
public class HibernateRantDao extends HibernateDaoSupport implements RantDao {
   private static final String MOTORIST = Motorist.class.getName();
   private static final String RANT = Rant.class.getName();
   private static final String VEHICLE = Vehicle.class.getName();

   public HibernateRantDao() {}

   public Vehicle findVehicleByPlate(String state, String plateNumber) {
      List results = getHibernateTemplate()
                        .find(
                                          "from "
                                                            + VEHICLE
                                                            + " where state = ? and plateNumber = ?",
                                          new Object[] { state, plateNumber });

      if (results.size() > 0) {
         return (Vehicle) results.get(0);
      }

      return null; // TODO - Should I throw an exception instead?
   }

   public List<Rant> getAllRants() {
      return getHibernateTemplate().find("from " + RANT);
   }

   public Motorist getMotoristByEmail(String email) {
      List results = getHibernateTemplate().find(
                        "from " + MOTORIST + " where email = ?", email);

      if (results.size() > 0) {
         return (Motorist) results.get(0);
      }
      return null; // TODO - Should I throw an exception instead?
   }

   public void saveRant(Rant rant) {
      getHibernateTemplate().saveOrUpdate(rant);
   }

   public void saveVehicle(Vehicle vehicle) {
      getHibernateTemplate().saveOrUpdate(vehicle);
   }

   public List<Rant> getRantsForDay(Date day) {
      return getHibernateTemplate().find(
                        "from " + RANT + " where postedDate = ?", day);
   }

   public void saveMotorist(Motorist driver) {
      getHibernateTemplate().saveOrUpdate(driver);
   }

   public Motorist getDriverById(Integer id) {
      return (Motorist) getHibernateTemplate().load(Motorist.class, id);
   }

   public int getMotoristCount() {
      // TODO Auto-generated method stub
      return 0;
   }
}
