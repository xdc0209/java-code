package com.roadrantz.dao.ibatis;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class IBatisTemplateRantDao implements RantDao
{
    public IBatisTemplateRantDao()
    {
    }

    public void saveRant(Rant rant)
    {
        sqlMapClientTemplate.insert("insertRant", rant);
    }

    @SuppressWarnings("unchecked")
    public List<Rant> getAllRants()
    {
        return sqlMapClientTemplate.queryForList("getAllRants", null);
    }

    @SuppressWarnings("unchecked")
    public List<Rant> getRantsForDay(Date day)
    {
        return sqlMapClientTemplate.queryForList("getRantsForDay", day);
    }

    public Vehicle findVehicleByPlate(String state, String plateNumber)
    {
        Vehicle queryParam = new Vehicle();
        queryParam.setState(state);
        queryParam.setPlateNumber(plateNumber);

        return (Vehicle) sqlMapClientTemplate.queryForObject("findVehicleByPlate", queryParam);
    }

    public void saveVehicle(Vehicle vehicle)
    {
        sqlMapClientTemplate.insert("insertVehicle", vehicle);
    }

    public Motorist getMotoristByEmail(String email)
    {
        return (Motorist) sqlMapClientTemplate.queryForObject("findMotoristByEmail", email);
    }

    public void saveMotorist(Motorist driver)
    {
        Integer primaryKey = (Integer) sqlMapClientTemplate.insert("insertMotorist", driver);
    }

    public int getMotoristCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    // injected
    private SqlMapClientTemplate sqlMapClientTemplate;

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate)
    {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }
}
