package com.roadrantz.dao.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

/**
 * RantDao implementation that uses JPA by extending Spring's JpaDaoSupport
 * class and using the supplied JpaTemplate.
 * 
 * Among other things, JpaTemplate offers several convenience methods (such as
 * <code>find()</code>) as well as throwing subclasses of Spring's
 * platform-neutral DataAccessException.
 * 
 * The downside of using JpaTemplate (and JpaDaoSupport) is that JpaRantDao is
 * coupled to the Spring API. For an almost Spring-free approach, take a look at
 * PureJpaRantDao.
 * 
 * @author wallsc
 */
public class JpaRantDao extends JpaDaoSupport implements RantDao
{
    public JpaRantDao()
    {
    }

    public void saveRant(Rant rant)
    {
        getJpaTemplate().persist(rant);
    }

    @SuppressWarnings("unchecked")
    public List<Rant> getAllRants()
    {
        return getJpaTemplate().find("select r from Rant r");
    }

    @SuppressWarnings("unchecked")
    public List<Rant> getRantsForDay(Date day)
    {
        return getJpaTemplate().find("select r from Rant r where r.postedDate=?1", day);
    }

    public Vehicle findVehicleByPlate(String state, String plateNumber)
    {
        List matches = getJpaTemplate().find("select v from Vehicle v where v.state=?1 and v.plateNumber=?2", state,
                plateNumber);

        return (matches.size() > 0) ? (Vehicle) matches.get(0) : null;
    }

    public void saveVehicle(Vehicle vehicle)
    {
        getJpaTemplate().persist(vehicle);
    }

    public Motorist getMotoristByEmail(String email)
    {
        List matches = getJpaTemplate().find("select d from Motorist d where d.email=?1", email);

        return (matches.size() > 0) ? (Motorist) matches.get(0) : null;
    }

    public void saveMotorist(Motorist driver)
    {
        getJpaTemplate().persist(driver);
    }

    public int getMotoristCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
