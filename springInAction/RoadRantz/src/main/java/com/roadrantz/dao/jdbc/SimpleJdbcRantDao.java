package com.roadrantz.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class SimpleJdbcRantDao extends SimpleJdbcDaoSupport implements RantDao {
   private static final String RANT_INSERT = "insert into rant (id, rantText, vehicle_id, postedDate) "
                     + "values (null,?,?,?)";

   private static final String RANT_SELECT = "select id, rantText, vehicle_id, postedDate from rant";

   private static final String RANT_FOR_DAY_SELECT = RANT_SELECT
                     + " where postedDate=?";

   private static final String VEHICLE_SELECT = "select id, plateNumber, state from vehicle";

   private static final String VEHICLE_BY_ID_SELECT = VEHICLE_SELECT
                     + " where id=?";

   private static final String VEHICLE_BY_PLATE_SELECT = VEHICLE_SELECT
                     + " where state=? and plateNumber=?";

   private static final String VEHICLE_INSERT = "insert into vehicle (id, plateNumber, state) "
                     + "values (null,?,?)";

   private static final String MOTORIST_SELECT = "select id, email, password, firstName, lastName from motorist";

   private static final String MOTORIST_BY_EMAIL_SELECT = MOTORIST_SELECT
                     + " where email=?";

   private static final String MOTORIST_BY_ID_SELECT = MOTORIST_SELECT
                     + " where id=?";

   private static final String MOTORIST_INSERT = "insert into motorist (id, email, password, firstName, lastName) "
                     + "values (null, ?,?,?,?)";

   public SimpleJdbcRantDao() {}

   public void saveRant(Rant rant) {
      getSimpleJdbcTemplate().update(RANT_INSERT, rant.getRantText(),
                        rant.getVehicle().getId(), rant.getPostedDate());

      rant.setId(queryForIdentity());
   }

   public List<Rant> getAllRants() {
      return getSimpleJdbcTemplate().query(RANT_SELECT,
                        new ParameterizedRowMapper<Rant>() {
                           public Rant mapRow(ResultSet rs, int rowNum)
                                             throws SQLException {
                              Rant rant = new Rant();

                              rant.setId(rs.getInt(1));
                              rant.setRantText(rs.getString(2));
                              rant.setPostedDate(rs.getDate(4));
                              Vehicle vehicle = findVehicleById(rs.getInt(3));
                              rant.setVehicle(vehicle);
                              return rant;
                           }
                        });
   }

   public Vehicle findVehicleById(Integer id) {
      return getSimpleJdbcTemplate().queryForObject(VEHICLE_BY_ID_SELECT,
                        new ParameterizedRowMapper<Vehicle>() {
                           public Vehicle mapRow(ResultSet rs, int rowNum)
                                             throws SQLException {
                              Vehicle vehicle = new Vehicle();

                              vehicle.setId(rs.getInt(1));
                              vehicle.setPlateNumber(rs.getString(2));
                              vehicle.setState(rs.getString(3));

                              return vehicle;
                           }
                        }, new Object[] { id });
   }

   public List<Rant> getRantsForDay(Date day) {
      return getSimpleJdbcTemplate().query(RANT_FOR_DAY_SELECT,
      // TODO - THIS IS COMMON...SHOULDN'T BE AN AIC
                        new ParameterizedRowMapper<Rant>() {
                           public Rant mapRow(ResultSet rs, int rowNum)
                                             throws SQLException {
                              Rant rant = new Rant();

                              rant.setId(rs.getInt(1));
                              rant.setRantText(rs.getString(2));
                              rant.setPostedDate(rs.getDate(4));
                              Vehicle vehicle = findVehicleById(rs.getInt(3));
                              rant.setVehicle(vehicle);
                              return rant;
                           }
                        }, day);
   }

   public Vehicle findVehicleByPlate(String state, String plateNumber) {
      List<Vehicle> matches = getSimpleJdbcTemplate().query(
                        VEHICLE_BY_PLATE_SELECT,
                        new ParameterizedRowMapper<Vehicle>() {
                           public Vehicle mapRow(ResultSet rs, int rowNum)
                                             throws SQLException {
                              Vehicle vehicle = new Vehicle();

                              vehicle.setId(rs.getInt(1));
                              vehicle.setPlateNumber(rs.getString(2));
                              vehicle.setState(rs.getString(3));

                              return vehicle;
                           }
                        }, new Object[] { state, plateNumber });

      return (matches.size() > 0) ? matches.get(0) : null;
   }

   public void saveVehicle(Vehicle vehicle) {
      getSimpleJdbcTemplate().update(VEHICLE_INSERT, vehicle.getPlateNumber(),
                        vehicle.getState());
      vehicle.setId(queryForIdentity());
   }

   public Motorist getMotoristByEmail(String email) {
      List<Motorist> matches = getSimpleJdbcTemplate().query(
                        MOTORIST_BY_EMAIL_SELECT,
                        new ParameterizedRowMapper<Motorist>() {
                           public Motorist mapRow(ResultSet rs, int rowNum)
                                             throws SQLException {
                              Motorist motorist = new Motorist();

                              motorist.setId(rs.getInt(1));
                              motorist.setEmail(rs.getString(2));
                              motorist.setPassword(rs.getString(3));
                              motorist.setFirstName(rs.getString(4));
                              motorist.setLastName(rs.getString(5));

                              return motorist;
                           }
                        }, email);

      return (matches.size() > 0) ? matches.get(0) : null;
   }

   /**
    * Retrieves a Motorist from the database using Spring's SimpleJdbcTemplate,
    * which allows for handy Java 5 features such as generics, autoboxing, and
    * variable arguments.
    * 
    * From Listing 5.7
    * 
    * @param id
    *           The ID of the Motorist to be retrieved
    * @return The Motorist
    */
   public Motorist getMotoristById(long id) {
      List<Motorist> matches = getSimpleJdbcTemplate().query(
                        MOTORIST_BY_ID_SELECT,
                        new ParameterizedRowMapper<Motorist>() {
                           public Motorist mapRow(ResultSet rs, int rowNum)
                                             throws SQLException {
                              Motorist motorist = new Motorist();

                              motorist.setId(rs.getInt(1));
                              motorist.setEmail(rs.getString(2));
                              motorist.setPassword(rs.getString(3));
                              motorist.setFirstName(rs.getString(4));
                              motorist.setLastName(rs.getString(5));
                              return motorist;
                           }
                        }, id);

      return matches.size() > 0 ? matches.get(0) : null;
   }

   public void saveMotorist(Motorist motorist) {
      getSimpleJdbcTemplate().update(MOTORIST_INSERT, motorist.getEmail(),
                        motorist.getPassword(), motorist.getFirstName(),
                        motorist.getLastName());
      motorist.setId(queryForIdentity());
   }

   private Integer queryForIdentity() {
      return new Integer(getJdbcTemplate().queryForInt("call Identity()"));
   }

   public int getMotoristCount() {
      // TODO Auto-generated method stub
      return 0;
   }
}
