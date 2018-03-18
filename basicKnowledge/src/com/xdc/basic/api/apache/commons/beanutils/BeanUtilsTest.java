package com.xdc.basic.api.apache.commons.beanutils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

/**
 * bean必须实现get和set方法。
 * 
 * @author xdc
 * 
 */
public class BeanUtilsTest
{
    /**
     * 把orig中的值copy到dest中。
     * 
     * @throws Exception
     */
    @Test
    public void testCopyProperties() throws Exception
    {
        Person origPerson = new Person("xdc", 21, false, new Date());
        Person destPerson = new Person();

        // Ali-P3C-AvoidApacheBeanUtilsCopyRule：Apache BeanUtils性能较差，可以使用其他方案比如Spring BeanUtils, Cglib BeanCopier。
        // BeanUtils.copyProperties(destPerson, origPerson);
        org.springframework.beans.BeanUtils.copyProperties(origPerson, destPerson);
    }

    /**
     * 设置Bean对象的名称为name的property的值为value.
     * 
     * @throws Exception
     */
    @Test
    public void testSetProperty() throws Exception
    {
        Person person = new Person();
        BeanUtils.setProperty(person, "name", "xdc");
        BeanUtils.setProperty(person, "age", 25);
        BeanUtils.setProperty(person, "smoker", false);
        BeanUtils.setProperty(person, "birth", new Date());
    }

    /**
     * 把Bean的属性值放入到一个Map里面。
     * 
     * @throws Exception
     */
    @Test
    public void testDescribe() throws Exception
    {
        Person person = new Person("xdc", 21, false, new Date());

        @SuppressWarnings({ "unchecked", "unused" })
        Map<String, Object> map = BeanUtils.describe(person);
    }

    /**
     * 把map中的properties里面的值放入bean中。
     * 
     * @throws Exception
     */
    @Test
    public void testPopulate() throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "xdc");
        map.put("age", 25);
        map.put("birth", new Date());

        Person person = new Person();
        BeanUtils.populate(person, map);
        System.out.println(person);
    }
}
