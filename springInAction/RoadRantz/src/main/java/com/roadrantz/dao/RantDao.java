package com.roadrantz.dao;

import java.util.Date;
import java.util.List;

import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.roadrantz.domain.Motorist;
import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public interface RantDao
{
    @CacheFlush(modelId = "rantzCacheModel")
    public void saveRant(Rant rant);

    @Cacheable(modelId = "rantzCacheModel")
    public List<Rant> getAllRants();

    @Cacheable(modelId = "rantzCacheModel")
    public List<Rant> getRantsForDay(Date day);

    public Vehicle findVehicleByPlate(String state, String plateNumber);

    public void saveVehicle(Vehicle vehicle);

    public Motorist getMotoristByEmail(String email);

    public void saveMotorist(Motorist driver);

    public int getMotoristCount();
}
