package com.roadrantz.dao.jdbc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class NamedParameterJdbcRantDao extends NamedParameterJdbcDaoSupport
                  implements RantDao {

   private static final String MOTORIST_SELECT = "select id, email, password, firstName, lastName from motorist";

   private static final String MOTORIST_INSERT = "insert into motorist (id, email, password, firstName, lastName) "
                     + "values (null, :email, :password, :firstName, :lastName)";

   public NamedParameterJdbcRantDao() {}

   public void saveRant(Rant rant) {
   // TODO Auto-generated method stub

   }

   public List<Rant> getAllRants() {
      // TODO Auto-generated method stub
      return null;
   }

   public List<Rant> getRantsForDay(Date day) {
      // TODO Auto-generated method stub
      return null;
   }

   public Vehicle findVehicleByPlate(String state, String plateNumber) {
      // TODO Auto-generated method stub
      return null;
   }

   public void saveVehicle(Vehicle vehicle) {
   // TODO Auto-generated method stub

   }

   public Motorist getMotoristByEmail(String email) {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * Saves a Motorist object using Spring's NamedParameterJdbcTemplate.
    * 
    * From Listing 5.6
    * 
    * @param motorist
    *           The Motorist object to be saved.
    */
   public void saveMotorist(Motorist motorist) {
      Map<String, String> parameters = new HashMap<String, String>();
      parameters.put("email", motorist.getEmail());
      parameters.put("password", motorist.getPassword());
      parameters.put("firstName", motorist.getFirstName());
      parameters.put("lastName", motorist.getLastName());
      getNamedParameterJdbcTemplate().update(MOTORIST_INSERT, parameters);
   }

   public int getMotoristCount() {
      // TODO Auto-generated method stub
      return 0;
   }
}
