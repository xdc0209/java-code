package com.roadrantz.dao.jdbc;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class IdentityStoredProcedure extends StoredProcedure {
  private static final String PROC_NAME = "identity";
  
  public IdentityStoredProcedure(DataSource ds) {
    setDataSource(ds);
    setSql(PROC_NAME);

    // Parameters should be declared in same order here that
    // they are declared in the stored procedure. 

    declareParameter(new SqlOutParameter( "value",
                       Types.INTEGER ) );
    compile();
  }
  
  public Integer getIdentity() {
    Map map = execute(new HashMap());
    
    return (Integer) map.get("value");
  }
}
