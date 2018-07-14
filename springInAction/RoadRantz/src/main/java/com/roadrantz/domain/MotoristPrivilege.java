package com.roadrantz.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MOTORIST_PRIVILEGES")
@SuppressWarnings("serial")
public class MotoristPrivilege implements Serializable
{
    private Integer  id;
    private Motorist motorist;
    private String   privilege;

    public MotoristPrivilege()
    {
    }

    public MotoristPrivilege(String privilege)
    {
        this.privilege = privilege;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @ManyToOne
    public Motorist getMotorist()
    {
        return motorist;
    }

    public void setMotorist(Motorist motorist)
    {
        this.motorist = motorist;
    }

    public String getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege(String privilege)
    {
        this.privilege = privilege;
    }
}
