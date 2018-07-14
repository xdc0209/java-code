package com.roadrantz.dao.ibatis;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.roadrantz.dao.RantDao;
import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class IBatisRantDao extends SqlMapClientDaoSupport implements RantDao
{
    public IBatisRantDao()
    {
    }

    public void saveRant(Rant rant)
    {
        getSqlMapClientTemplate().insert("insertRant", rant);
    }

    @SuppressWarnings("unchecked")
    public List<Rant> getAllRants()
    {
        return getSqlMapClientTemplate().queryForList("getAllRants", null);
    }

    @SuppressWarnings("unchecked")
    public List<Rant> getRantsForDay(Date day)
    {
        return getSqlMapClientTemplate().queryForList("getRantsForDay", day);
    }

    public Vehicle findVehicleByPlate(String state, String plateNumber)
    {
        Vehicle queryParam = new Vehicle();
        queryParam.setState(state);
        queryParam.setPlateNumber(plateNumber);

        return (Vehicle) getSqlMapClientTemplate().queryForObject("findVehicleByPlate", queryParam);
    }

    public void saveVehicle(Vehicle vehicle)
    {
        getSqlMapClientTemplate().insert("insertVehicle", vehicle);
    }

    public Motorist getMotoristByEmail(String email)
    {
        return (Motorist) getSqlMapClientTemplate().queryForObject("findMotoristByEmail", email);
    }

    public void saveMotorist(Motorist driver)
    {
        Integer primaryKey = (Integer) getSqlMapClientTemplate().insert("insertMotorist", driver);
    }

    public int getMotoristCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
