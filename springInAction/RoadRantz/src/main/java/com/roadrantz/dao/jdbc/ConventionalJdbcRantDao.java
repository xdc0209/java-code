package com.roadrantz.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class ConventionalJdbcRantDao implements RantDao
{
    public ConventionalJdbcRantDao()
    {
    }

    private static final String RANT_INSERT     = "insert into rant (id, rantText, vehicle_id, postedDate) "
            + "values (null,?,?,?)";

    private static final String MOTORIST_INSERT = "insert into motorist (id, email, password, firstName, lastName) "
            + "values (null, ?,?,?,?)";

    public void saveRant(Rant rant)
    {
        try
        {
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("");
            stmt.setString(1, rant.getRantText());
            stmt.setInt(2, rant.getVehicle().getId());
            stmt.setDate(3, new java.sql.Date(rant.getPostedDate().getTime()));
            stmt.execute();
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Rant> getAllRants()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Rant> getRantsForDay(Date day)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Vehicle findVehicleByPlate(String state, String plateNumber)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void saveVehicle(Vehicle vehicle)
    {
        // TODO Auto-generated method stub
    }

    public Motorist getMotoristByEmail(String email)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Inserts a motorist to the database using conventional (e.g., non-Spring)
     * JDBC.
     * 
     * From Listing 5.1
     * 
     * @param motorist
     *            The Motorist object to insert
     */
    public void saveMotorist(Motorist motorist)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(MOTORIST_INSERT);

            stmt.setString(1, motorist.getEmail());
            stmt.setString(2, motorist.getPassword());
            stmt.setString(3, motorist.getFirstName());
            stmt.setString(4, motorist.getLastName());
            stmt.execute();
        }
        catch (SQLException e)
        {
            // deal with exception--somehow
        }
        finally
        {
            try
            {
                stmt.close();
                conn.close();
            }
            catch (SQLException e)
            {
            }
        }
    }

    private static final String MOTORIST_UPDATE = "update motorist "
            + "set email=?, password=?, firstName=?, lastName=? " + "where id=?";

    /**
     * Updates a Motorist object in the database, using convention (e.g.,
     * non-Spring) JDBC
     * 
     * From Listing 5.2
     * 
     * @param motorist
     *            The Motorist object to update
     */
    public void updateMotorist(Motorist motorist)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(MOTORIST_UPDATE);
            stmt.setString(1, motorist.getEmail());
            stmt.setString(2, motorist.getPassword());
            stmt.setString(3, motorist.getFirstName());
            stmt.setString(4, motorist.getLastName());
            stmt.setInt(5, motorist.getId());
            stmt.execute();
        }
        catch (SQLException e)
        {
            // deal with exception--somehow
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
            }
        }
    }

    private static final String MOTORIST_QUERY = "select id, email, password, firstName, lastName "
            + " from motorist where id=?";

    /**
     * Retrieves a Motorist from the database using conventional (e.g.,
     * non-Spring) JDBC.
     * 
     * From Listing 5.3
     * 
     * @param id
     *            The ID of the Motorist to retrieve
     * @return
     */
    public Motorist getMotoristById(Integer id)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(MOTORIST_QUERY);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Motorist motorist = null;
            if (rs.next())
            {
                motorist = new Motorist();
                motorist.setId(rs.getInt("id"));
                motorist.setEmail(rs.getString("email"));
                motorist.setPassword(rs.getString("password"));
                motorist.setFirstName(rs.getString("firstName"));
                motorist.setLastName(rs.getString("lastName"));
            }
            return motorist;
        }
        catch (SQLException e)
        {
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
            }
        }
        return null;
    }

    private int queryForIdentity()
    {
        Connection conn = null;
        CallableStatement stmt;
        ResultSet rs;

        try
        {
            stmt = conn.prepareCall("call identity()");
            rs = stmt.executeQuery();
            if (rs.next())
            {
                return rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
        }
        return 0;
    }

    public int getMotoristCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
}
