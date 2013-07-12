package com.roadrantz.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class JdbcRantDaoWithSqlObjects extends JdbcDaoSupport 
    implements RantDao {
  
  private static final String RANT_INSERT = 
    "insert into rant (id, rantText, vehicle_id, postedDate) " +
    "values (null,?,?,?)";

  private static final String RANT_SELECT =
      "select id, rantText, vehicle_id, postedDate from rant";
  
  private static final String RANT_FOR_DAY_SELECT =
      RANT_SELECT + " where postedDate=?";
  
  private static final String VEHICLE_SELECT =
      "select id, plateNumber, state, motorist_id from vehicle";
  
  private static final String VEHICLE_BY_ID_SELECT =
      VEHICLE_SELECT + " where id=?";
  
  private static final String VEHICLE_BY_PLATE_SELECT =
    VEHICLE_SELECT + " where state=:state and plateNumber=:plateNumber";
  
  private static final String VEHICLE_INSERT =
      "insert into vehicle (id, plateNumber, state, motorist_id) " +
      "values (null,?,?,?)";
  
  private static final String MOTORIST_SELECT =
      "select id, email, password, firstName, lastName from motorist";
  
  private static final String MOTORIST_BY_EMAIL_SELECT =
      MOTORIST_SELECT + " where email=?";

  private static final String MOTORIST_BY_ID_SELECT =
    MOTORIST_SELECT + " where id=?";
  
  private static final String MOTORIST_INSERT =
      "insert into motorist (id, email, password, firstName, lastName) " +
      "values (null, ?,?,?,?)";  

  private MotoristByIdQuery motoristByIdQuery;
  private MotoristByEmailQuery motoristByEmailQuery;
  private VehicleByIdQuery vehicleByIdQuery;
  private VehicleByPlateQuery vehicleByPlateQuery;
  private VehicleInsert vehicleInsert;
  private RantForDayQuery rantForDayQuery;
  private RantQuery rantQuery;
  
  protected void initDao() throws Exception {
    motoristByIdQuery = new MotoristByIdQuery(getDataSource());
    motoristByEmailQuery = new MotoristByEmailQuery(getDataSource());
    vehicleByIdQuery = new VehicleByIdQuery(getDataSource());
    vehicleByPlateQuery = new VehicleByPlateQuery(getDataSource());
    rantForDayQuery = new RantForDayQuery(getDataSource());
    rantQuery = new RantQuery(getDataSource());
    vehicleInsert = new VehicleInsert(getDataSource());
  }

  public void saveMotorist(Motorist motorist) {
    getJdbcTemplate().update(MOTORIST_INSERT,
        new Object[] { motorist.getEmail(), motorist.getPassword(),
            motorist.getFirstName(), motorist.getLastName() });
    
    motorist.setId(queryForIdentity());
  }

  private int queryForIdentity() {
    return new Integer(getJdbcTemplate().queryForInt("call identity()"));
  }

  public void saveRant(Rant rant) {
    getJdbcTemplate().update(RANT_INSERT, 
        new Object[] {
            rant.getRantText(), 
            rant.getVehicle().getId(),
            rant.getPostedDate()
          });
    
    rant.setId(queryForIdentity());
  }
  
  public void saveVehicle(Vehicle vehicle) {
    vehicleInsert.insert(vehicle);    
    vehicle.setId(queryForIdentity());
  }

  @SuppressWarnings("unchecked")
  public List<Rant> getAllRants() {
    List rants = rantQuery.execute();
    loadVehiclesForRants(rants);
    return rants;
  }
  
  @SuppressWarnings("unchecked")
  public List<Rant> getRantsForDay(Date day) {
    List rants = rantForDayQuery.execute(new Object[] {day});
    loadVehiclesForRants(rants);
    return rants;
  }
  
  private void loadVehiclesForRants(List<Rant> rants) {
    for (Iterator iter = rants.iterator(); iter.hasNext();) {
      JdbcRant rant = (JdbcRant) iter.next();
      rant.setVehicle(findVehicleById(rant.getVehicleId()));
    }
  }

  public Vehicle findVehicleById(Integer id) {
    JdbcVehicle vehicle = (JdbcVehicle) vehicleByIdQuery.findObject(id);

    loadMotoristForVehicle(vehicle);

    return vehicle;
  }

  public Vehicle findVehicleByPlate(String state, String plateNumber) {
    Map<String,String> parameters = new HashMap<String,String>();
    parameters.put("state", state);
    parameters.put("plateNumber", plateNumber);
    JdbcVehicle vehicle = (JdbcVehicle) 
        vehicleByPlateQuery.findObjectByNamedParam(parameters);
    
    loadMotoristForVehicle(vehicle);
    
    return vehicle;
  }
  
  private void loadMotoristForVehicle(JdbcVehicle vehicle) {
    vehicle.setMotorist(getMotoristById(vehicle.getMotoristId()));
  }

  public Motorist getMotoristById(Integer id) {
    return (Motorist) motoristByIdQuery.findObject(id);  
  }
  
  public Motorist getMotoristByEmail(String email) {
    return (Motorist) motoristByEmailQuery.findObject(email);
  }


  // SQL Objects
  protected class RantQuery extends MappingSqlQuery {
    public RantQuery(DataSource dataSource) {
      this(dataSource, RANT_SELECT);  
    }
    
    public RantQuery(DataSource dataSource, String query) {
      super(dataSource, query);
    }
    
    protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
      JdbcRant rant = new JdbcRant();
      rant.setId(rs.getInt(1));
      rant.setRantText(rs.getString(2));
      rant.setVehicleId(rs.getInt(3));
      rant.setPostedDate(rs.getDate(4));
      return rant;
    }
  }
  
  protected class RantForDayQuery extends RantQuery {
    public RantForDayQuery(DataSource dataSource) {
      super(dataSource, RANT_FOR_DAY_SELECT);
      declareParameter(new SqlParameter(Types.DATE));
      compile();
    }
  }
  
  
  protected class VehicleQuery extends MappingSqlQuery {
    public VehicleQuery(DataSource dataSource, String query) {
      super(dataSource, query);
    }
    
    protected Object mapRow(ResultSet rs, int rowNum) 
        throws SQLException {
      JdbcVehicle vehicle = new JdbcVehicle();      
      vehicle.setId(rs.getInt(1));
      vehicle.setPlateNumber(rs.getString(2));
      vehicle.setState(rs.getString(3));
      vehicle.setMotoristId(rs.getInt(4));
      return vehicle;
    }
  }
  
  protected class VehicleByIdQuery extends VehicleQuery {
    public VehicleByIdQuery(DataSource dataSource) {
      super(dataSource, VEHICLE_BY_ID_SELECT);
      declareParameter(new SqlParameter(Types.INTEGER));
      compile();
    }
  }

  protected class VehicleByPlateQuery extends VehicleQuery {
    public VehicleByPlateQuery(DataSource dataSource) {
      super(dataSource, VEHICLE_BY_PLATE_SELECT);
      declareParameter(new SqlParameter(Types.VARCHAR));
      declareParameter(new SqlParameter(Types.VARCHAR));
      compile();
    }
  }

  
  protected class MotoristQuery extends MappingSqlQuery {
    public MotoristQuery(DataSource dataSource, String query) {
      super(dataSource, query);
    }
    
    protected Object mapRow(ResultSet rs, int rowNum) 
        throws SQLException {
      Motorist motorist = new Motorist();
      
      motorist.setId(rs.getInt(1));
      motorist.setEmail(rs.getString(2));
      motorist.setPassword(rs.getString(3));
      motorist.setFirstName(rs.getString(4));
      motorist.setLastName(rs.getString(5));
      
      return motorist;
    }
  }
  
  protected class MotoristByEmailQuery extends MotoristQuery {
    public MotoristByEmailQuery(DataSource dataSource) {
      super(dataSource, MOTORIST_BY_EMAIL_SELECT);
      declareParameter(new SqlParameter(Types.VARCHAR));
      compile();
    }
  }
  
  protected class MotoristByIdQuery extends MotoristQuery {
    public MotoristByIdQuery(DataSource dataSource) {
      super(dataSource, MOTORIST_BY_ID_SELECT);
      declareParameter(new SqlParameter(Types.INTEGER));
      compile();
    }
  }  
  
  protected class VehicleInsert extends SqlUpdate {
    public VehicleInsert(DataSource dataSource) {
      super(dataSource, VEHICLE_INSERT);
      declareParameter(new SqlParameter(Types.VARCHAR));
      declareParameter(new SqlParameter(Types.VARCHAR));
      declareParameter(new SqlParameter(Types.INTEGER));
      compile();
    }
    
    public void insert(Vehicle vehicle) {
      update(new Object[] {
          vehicle.getPlateNumber(), 
          vehicle.getState(), 
          vehicle.getMotorist().getId()
        });
    }
  }
  
  public int getMotoristCount() {
    // TODO Auto-generated method stub
    return 0;
  }  
}
