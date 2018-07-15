package com.xdc.basic.skills.finderrorcode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FindErrorCode
{
    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException
    {
        // 存放待抽取错误码类
        ArrayList<String> classArrayList = new ArrayList<String>();
        // 存放抽出出来的错误码
        ArrayList<ErrorCodeElement> errorCodeElementsArrayList = new ArrayList<ErrorCodeElement>();

        // 在这添加完整类名
        classArrayList.add("com.xdc.errorcode.InstanceErrorCode");
        classArrayList.add("com.xdc.errorcode.ImageError");
        classArrayList.add("com.xdc.errorcode.SnapshotErrorCode");
        classArrayList.add("com.xdc.errorcode.EBSErrorCode");
        classArrayList.add("com.xdc.errorcode.IpError");
        classArrayList.add("com.xdc.errorcode.KeyPairErrorCode");
        classArrayList.add("com.xdc.errorcode.SpecError");

        // 抽取每个类中的错误码
        for (int i = 0; i < classArrayList.size(); i++)
        {
            FindErrorCode.generateErrorCode(classArrayList.get(i), errorCodeElementsArrayList);
        }

        // 将结果排序
        Collections.sort(errorCodeElementsArrayList, new Comparator<ErrorCodeElement>()
        {
            @Override
            public int compare(ErrorCodeElement arg0, ErrorCodeElement arg1)
            {
                return arg0.getErrorValue() - arg1.getErrorValue();
            }
        });

        // 显示结果
        for (int i = 0; i < errorCodeElementsArrayList.size(); i++)
        {
            System.out.println(errorCodeElementsArrayList.get(i));
        }
    }

    /**
     * 抽取错误码的方法, 这个方法只针对以整型常量定义错误码的方式
     * 
     * @param className
     * @param errorCodeElements
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void generateErrorCode(String className, ArrayList<ErrorCodeElement> errorCodeElements)
            throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException
    {
        Class<?> myClass = Class.forName(className);
        Field[] fields = myClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            if (field.getType().toString().equals("int"))
            {
                String errorString = field.getName() + " ---- "
                        + field.getDeclaringClass().toString().substring("class ".length());
                int errorValue = field.getInt(Class.forName(className).newInstance());
                errorCodeElements.add(new ErrorCodeElement(errorString, errorValue));
            }
        }
    }
}
