package com.xdc.basic.api.apache.commons.jxpath;

import org.apache.commons.jxpath.JXPathContext;

public class JXPathTest
{
    public static void main(String[] args)
    {
        Address address = new Address();
        address.setStreetNumber("1302");

        String[] hobbies = new String[] { "basketball", "football" };

        Employee emp = new Employee();
        emp.setFirstName("Xu");
        emp.setHomeAddress(address);
        emp.setHobbies(hobbies);

        // access properties of a JavaBean
        JXPathContext context = JXPathContext.newContext(emp);
        String firstName = (String) context.getValue("firstName");
        System.out.println(firstName);

        // access a property of a nested bean
        String streetNumber = (String) context.getValue("homeAddress/streetNumber");
        System.out.println(streetNumber);
        Address addr = (Address) context.getValue("homeAddress");
        System.out.println(addr == address);

        // extract elements from arrays and collections
        // Note: in XPath the first element of a collection has index 1, not 0.
        String hobby = (String) context.getValue("hobbies[1]");
        System.out.println(hobby);

        // modify property values
        context.setValue("firstName", "Xi");
        System.out.println(emp.getFirstName());
    }
}
