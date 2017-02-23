package com.xdc.basic.api.apache.commons.lang3.builder;

public class BuilderTest
{
    public static void main(String[] args)
    {
        // Person ========================================
        Person person = new Person("xdc", 25, false);
        Person person2 = new Person("xdc", 25, false);
        Person person3 = new Person("gx", 25, false);

        System.out.println(person.equals(person2));
        System.out.println(person.equals(person3));

        System.out.println(person.toString());
        System.out.println(person3.toString());

        // PersonNotApache ===============================
        PersonNotApache personNotApache = new PersonNotApache("xdc", 25, false);
        System.out.println(personNotApache.toString());
        System.out.println(personNotApache.toString2());

        // PersonReflection ==============================
        PersonReflection personReflection = new PersonReflection("xdc", 25, false);
        PersonReflection personReflection2 = new PersonReflection("xdc", 25, false);
        PersonReflection personReflection3 = new PersonReflection("gx", 25, false);

        System.out.println(personReflection.equals(personReflection2));
        System.out.println(personReflection.equals(personReflection3));

        System.out.println(personReflection.toString());
        System.out.println(personReflection3.toString());

        // PersonReflectionToStringBuilder ===============
        PersonReflectionToStringBuilder p = new PersonReflectionToStringBuilder("xdc", 25, false);
        System.out.println(p.toString());
        System.out.println(p.toString2());
        System.out.println(p.toString3());
    }
}
