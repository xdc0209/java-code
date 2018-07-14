package com.springinaction.chapter03.propeditor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertyEditorMain
{
    public static void main(String[] args) throws Exception
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "com/springinaction/chapter03/propeditor/propEditorDemo.xml");

        Contact contact = (Contact) ctx.getBean("contact");
        PhoneNumber phoneNumber = contact.getPhoneNumber();
        System.out.println("Phone number:  " + "(" + phoneNumber.getAreaCode() + ") " + phoneNumber.getPrefix() + "-"
                + phoneNumber.getNumber());
    }
}
