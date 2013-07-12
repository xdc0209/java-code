package com.roadrantz.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * RantDao implementation that only uses JPA. Contrast this to JpaRantDao which
 * extends JpaDaoSupport and uses JpaTemplate from Spring's API. The only tie to
 * Spring in PureJpaRantDao is the Repository annotation, which is used to guide
 * Spring in converting JPA-specific exceptions to subclasses of Spring's
 * platform-neutral DataAccessException.
 * 
 * The EntityManager is injected here by PersistenceAnnotationBeanPostProcessor,
 * configured in the Spring context along with the entity manager factory.
 * 
 * Upon looking back at the book, I discovered that I don't discuss this
 * implementation at all in the book. That's unfortunate, since the Pure JPA
 * approach is considered the preferred approach to using JPA in Spring.
 * Nevertheless, here's the implementation for your enjoyment.
 * 
 * @author wallsc
 */
@Repository
public class PureJpaRantDao implements RantDao {
   public PureJpaRantDao() {}

   private EntityManager entityManager;

   @PersistenceContext
   public void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
   }

   public void saveRant(Rant rant) {
      entityManager.persist(rant);
   }

   @SuppressWarnings("unchecked")
   public List<Rant> getAllRants() {
      return entityManager.createQuery("select r from Rant r").getResultList();
   }

   @SuppressWarnings("unchecked")
   public List<Rant> getRantsForDay(Date day) {
      return entityManager.createQuery(
                        "select r from Rant r where r.postedDate=?1")
                        .setParameter(1, day).getResultList();
   }

   public Vehicle findVehicleByPlate(String state, String plateNumber) {
      List matches = entityManager.createQuery(
                        "select v from Vehicle v where v.state=?1 "
                                          + "and v.plateNumber=?2")
                        .setParameter(1, state).setParameter(2, plateNumber)
                        .getResultList();

      return (matches.size() > 0) ? (Vehicle) matches.get(0) : null;
   }

   public void saveVehicle(Vehicle vehicle) {
      entityManager.persist(vehicle);
   }

   public Motorist getMotoristByEmail(String email) {
      List matches = entityManager.createQuery(
                        "select d from Motorist d where d.email=?1")
                        .setParameter(1, email).getResultList();

      return (matches.size() > 0) ? (Motorist) matches.get(0) : null;
   }

   public void saveMotorist(Motorist driver) {
      entityManager.persist(driver);
   }

   public int getMotoristCount() {
      // TODO Auto-generated method stub
      return 0;
   }
}
