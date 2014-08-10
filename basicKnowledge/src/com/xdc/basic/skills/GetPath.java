package com.xdc.basic.skills;

public class GetPath
{
    /**
     * 获得当前类包名一致的路径
     * 
     * @return
     */
    public static String getPackagePath()
    {
        // 获取当前路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", "\\") + "\\";
        return packagePath;
    }

    /**
     * 获得相对于“用户的当前工作目录”的相对路径 （eclipse中“用户的当前工作目录”为该工程根目录）
     * 
     * @return
     */
    public static String getRelativePath()
    {
        // 获取当前路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", "\\") + "\\";

        // 获得相对路径
        String relativePath = "src\\" + packagePath;
        return relativePath;
    }

    /**
     * 获得绝对路径
     * 
     * @return
     */
    public static String getAbsolutePath()
    {
        // 获取当前路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", "\\") + "\\";

        // 获得相对路径
        String relativePath = "src\\" + packagePath;

        // 用户的当前工作目录
        String userDir = System.getProperty("user.dir");

        String absolutePath = userDir + "\\" + relativePath;
        return absolutePath;
    }

    public static void main(String[] args)
    {
        String packagePath = GetPath.getPackagePath();
        String relativePath = GetPath.getRelativePath();
        String absolutePath = GetPath.getAbsolutePath();

        System.out.println(packagePath);
        System.out.println(relativePath);
        System.out.println(absolutePath);
    }
}
