package com.xdc.basic.api.apache.commons.jxpath;

public class Employee
{
    private String   firstName;

    private Address  homeAddress;

    private String[] hobbies;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public Address getHomeAddress()
    {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress)
    {
        this.homeAddress = homeAddress;
    }

    public String[] getHobbies()
    {
        return hobbies;
    }

    public void setHobbies(String[] hobbies)
    {
        this.hobbies = hobbies;
    }
}
