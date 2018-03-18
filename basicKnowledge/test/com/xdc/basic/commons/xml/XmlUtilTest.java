package com.xdc.basic.commons.xml;

import org.junit.jupiter.api.Test;

import com.xdc.basic.api.jaxb.user.Users;

class XmlUtilTest
{
    @Test
    public void test()
    {
        String usersXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><users><user><name>小李</name><sex>男</sex><age>24</age></user><user><name>小丽</name><sex>女</sex><age>18</age></user><user><name>小王</name><sex>男</sex><age>22</age></user></users>";

        Users users = XmlUtil.fromXml(usersXML, Users.class);
        System.out.println(users);

        String xml = XmlUtil.toXml(users);
        System.out.println(xml);
    }
}
