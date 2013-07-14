package com.roadrantz.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * This class is an alternate implementation of HibernateRantDao, that more
 * closely resembles what was started in Listing 5.8.
 * 
 * Listing 5.8 starts talking about HibernateRantDao by injecting a
 * HibernateTemplate into an otherwise POJO-ish DAO. That example eventually
 * evolves into one that extends HibernateDaoSupport.
 * 
 * Although the name is different, this class serves as the example for how to
 * build Hibernate-based DAOs as described in section 5.4.2.
 * 
 * @author wallsc
 */
public class HibernateRantDaoUsingTemplate implements RantDao {
   private static final String MOTORIST = Motorist.class.getName();
   private static final String RANT = Rant.class.getName();
   private static final String VEHICLE = Vehicle.class.getName();

   public HibernateRantDaoUsingTemplate() {}

   public void saveVehicle(Vehicle vehicle) {
      hibernateTemplate.saveOrUpdate(vehicle);
   }

   public Vehicle findVehicleByPlate(String state, String plateNumber) {
      List results = hibernateTemplate.find("from " + VEHICLE
                        + " where state = ? and plateNumber = ?", new Object[] {
            state, plateNumber });

      if (results.size() > 0) {
         return (Vehicle) results.get(0);
      }

      return null; // TODO - Should I throw an exception instead?
   }

   public void saveRant(Rant rant) {
      hibernateTemplate.saveOrUpdate(rant);
   }

   public List<Rant> getAllRants() {
      return hibernateTemplate.loadAll(Rant.class);
   }

   public List<Rant> getRantsForDay(Date day) {
      return hibernateTemplate.loadAll(Rant.class);
   }

   public void saveMotorist(Motorist driver) {
      hibernateTemplate.saveOrUpdate(driver);
   }

   public Motorist getDriverById(Integer id) {
      return (Motorist) hibernateTemplate.load(Motorist.class, id);
   }

   public Motorist getMotoristByEmail(String email) {
      List results = hibernateTemplate.find("from " + MOTORIST
                        + " where email = ?", email);

      if (results.size() > 0) {
         return (Motorist) results.get(0);
      }
      return null; // TODO - Should I throw an exception instead?
   }

   public int getMotoristCount() {
      // TODO Auto-generated method stub
      return 0;
   }

   // injected
   private HibernateTemplate hibernateTemplate;

   public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
      this.hibernateTemplate = hibernateTemplate;
   }
}
