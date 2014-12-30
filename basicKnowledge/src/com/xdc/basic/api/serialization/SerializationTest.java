package com.xdc.basic.api.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.xdc.basic.skills.GetPath;

public class SerializationTest
{
    public static void main(String[] args)
    {
        String curPath = GetPath.getRelativePath();

        // Object serialization
        try
        {
            Student student1 = new Student("xdc", 25);
            System.out.println("student1: " + student1);

            FileOutputStream fos = new FileOutputStream(curPath + "serial");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(student1);

            oos.flush();
            oos.close();
        }
        catch (Exception e)
        {
            System.out.println("Exception during serialization:" + e);
            System.exit(0);
        }

        // Object deserialization
        try
        {
            Student student2;
            FileInputStream fis = new FileInputStream(curPath + "serial");
            ObjectInputStream ois = new ObjectInputStream(fis);

            student2 = (Student) ois.readObject();

            ois.close();
            System.out.println("student2: " + student2);
        }
        catch (Exception e)
        {
            System.out.println("Exception during deserialization:" + e);
            System.exit(0);
        }
    }

}
