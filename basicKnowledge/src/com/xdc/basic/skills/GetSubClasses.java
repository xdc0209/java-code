package com.xdc.basic.skills;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class GetSubClasses
{
    public static void main(String[] args)
    {
        try
        {
            List<Class<?>> subClasses = getSubClasses(Object.class, "com.xdc.basic.designpattern.observer");
            for (Class<?> clazz : subClasses)
            {
                System.out.println(clazz);
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取在指定包下某个class的所有非抽象子类
     * 
     * @param parentClass
     *            父类
     * @param packagePath
     *            指定包，格式如"com/iteye/strongzhu"
     * @return 该父类对应的所有子类列表
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getSubClasses(final Class<?> parentClass, final String packagePath)
            throws ClassNotFoundException
    {
        List<Class<?>> subClasses = new ArrayList<Class<?>>();

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        AssignableTypeFilter includeFilter = new AssignableTypeFilter(parentClass);
        provider.addIncludeFilter(includeFilter);
        Set<BeanDefinition> components = provider.findCandidateComponents(packagePath);
        for (BeanDefinition component : components)
        {
            Class<?> cls = Class.forName(component.getBeanClassName());
            if (Modifier.isAbstract(cls.getModifiers()))
            {
                continue;
            }
            subClasses.add(cls);
        }

        return subClasses;
    }
}
