package com.roadrantz.dao.jdbc;

import com.roadrantz.domain.Vehicle;

public class JdbcVehicle extends Vehicle
{
    public JdbcVehicle()
    {
    }

    private Integer motoristId;

    public Integer getMotoristId()
    {
        return motoristId;
    }

    public void setMotoristId(Integer motoristId)
    {
        this.motoristId = motoristId;
    }
}
