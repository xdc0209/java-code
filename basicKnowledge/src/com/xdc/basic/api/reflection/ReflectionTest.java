package com.xdc.basic.api.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class ReflectionTest
{
    /**
     * 反射基本样例
     */
    @Test
    public void reflectionDemo()
    {
        // 在JDK中，主要由以下类来实现Java反射机制，这些类都位于java.lang.reflect包中：
        // Class类：代表一个类。
        // Field类：代表类的成员变量（成员变量也称为类的属性）。
        // Method类：代表类的方法。
        // Constructor类：代表类的构造方法。
        // Array类：提供了动态创建数组，以及访问数组的元素的静态方法。

        // 要想使用反射，首先需要获得待处理类或对象所对应的Class对象。
        // 获取某个类或某个对象所对应的class对象的常用的3种方式：
        // 1. 使用Class类的静态方法forName：Class.forName(“java.lang.String”);
        // 2. 使用类的.class语法：String.class;
        // 3. 使用对象的getClass()方法：String s = “abc”; Class<?> clazz = s.getClass();

        // Class类是Reflection API中的核心类，它有以下方法
        // getName()：获得类的完整名字。
        // getFields()：获得类的public类型的属性。
        // getDeclaredFields()：获得类的所有属性。
        // getMethods()：获得类的public类型的方法。
        // getDeclaredMethods()：获得类的所有方法。
        // getMethod(String name,Class[]parameterTypes)：获得类的特定方法，name参数指定方法的名字，parameterTypes参数指定方法的参数类型。
        // getConstructors()：获得类的public类型的构造方法。
        // getDeclaredConstructors：获得类的所有构造方法。
        // getConstructor(Class[] parameterTypes)：获得类的特定构造方法，parameterTypes参数指定构造方法的参数类型。
        // newInstance()：通过类的不带参数的构造方法创建这个类的一个对象。

        // PS: declare的意思是声明，所以带declared的方法才是获得类中定义的相关属性和方法。

        Class<Student> clazz = Student.class;

        String name = clazz.getName();
        System.out.println(name);

        Field[] fields = clazz.getFields();
        System.out.println(ArrayUtils.toString(fields));

        Field[] declaredFields = clazz.getDeclaredFields();
        System.out.println(ArrayUtils.toString(declaredFields));

        Method[] methods = clazz.getMethods();
        System.out.println(ArrayUtils.toString(methods));

        Method[] declaredMethods = clazz.getDeclaredMethods();
        System.out.println(ArrayUtils.toString(declaredMethods));

        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println(ArrayUtils.toString(constructors));

        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        System.out.println(ArrayUtils.toString(declaredConstructors));
    }

    /**
     * 调用私有方法
     * 
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void invokePrivateMethod() throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Class<Student> clazz = Student.class;

        Constructor<Student> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Student student = constructor.newInstance("xudachao", 25);
        System.out.println(student);

        Method method = clazz.getDeclaredMethod("eat", String.class);
        method.setAccessible(true);
        method.invoke(student, "egg");
    }

    /**
     * 修改私有属性（即使没有set和get方法）
     * 
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     */
    @Test
    public void modifyPrivateField() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
    {
        Class<Student> clazz = Student.class;

        Constructor<Student> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Student student = constructor.newInstance("xudachao", 25);
        System.out.println(student);

        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(student, 26);
        System.out.println(student);
    }

    /**
     * 一个类对应的class对象只会加载一次
     * 
     * @throws ClassNotFoundException
     */
    @Test
    public void ClassLoaderTest() throws ClassNotFoundException
    {
        Class<String> c1 = String.class;

        Class<?> c2 = Class.forName("java.lang.String");

        String str = "Just do it.";
        Class<?> c3 = str.getClass();

        System.out.println(c1 == c2);
        System.out.println(c1 == c3);
    }
}
