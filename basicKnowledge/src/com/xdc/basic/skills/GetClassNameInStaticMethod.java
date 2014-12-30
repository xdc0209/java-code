package com.xdc.basic.skills;

/**
 * 在静态方法中获取类的名字
 * 
 * 引自：http://hi.baidu.com/kittopang/item/a04c9ed12ff32aefb2f77711
 * 
 * @author xdc
 * 
 */
public class GetClassNameInStaticMethod
{
    public static void main(String[] args)
    {
        // 方法1：通过SecurityManager的保护方法getClassContext()
        String clazzName = new SecurityManager()
        {
            public String getClassName()
            {
                return getClassContext()[1].getName();
            }
        }.getClassName();
        System.out.println(clazzName);

        // 方法2：通过Throwable的方法getStackTrace()
        String clazzName2 = new Throwable().getStackTrace()[0].getClassName();
        System.out.println(clazzName2);

        // 方法3：通过分析匿名类名称()
        String clazzName3 = new Object()
        {
            public String getClassName()
            {
                String clazzName = this.getClass().getName();
                return clazzName.substring(0, clazzName.lastIndexOf('$'));
            }
        }.getClassName();
        System.out.println(clazzName3);
    }

}
