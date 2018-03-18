package com.xdc.basic.commons;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class PathUtil
{
    private static String fileSpt = System.getProperty("file.separator");

    /**
     * 私有构造函数，防止误实例化。工具类不需要实例化
     */
    private PathUtil()
    {
    }

    /**
     * 获取与当前包名一致的路径
     * 
     * @return
     */
    public static String getPackagePath()
    {
        // 获取包路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", fileSpt) + fileSpt;
        return packagePath;
    }

    /**
     * 获取相对于“用户的当前工作目录”的相对路径（eclipse中“用户的当前工作目录”为该工程根目录）
     * 
     * @return
     */
    public static String getRelativePath()
    {
        // 获取包路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", fileSpt) + fileSpt;

        // 获取相对路径
        String relativePath = "src" + fileSpt + packagePath;
        return relativePath;
    }

    /**
     * 获取绝对路径
     * 
     * @return
     */
    public static String getAbsolutePath()
    {
        // 获取包路径
        String curClassName = new Throwable().getStackTrace()[1].getClassName();
        String curPackage = curClassName.substring(0, curClassName.lastIndexOf("."));
        String packagePath = curPackage.replace(".", fileSpt) + fileSpt;

        // 获取相对路径
        String relativePath = "src" + fileSpt + packagePath;

        // 获取用户的当前工作目录
        String userDir = System.getProperty("user.dir");

        // 获取绝对路径
        String absolutePath = userDir + fileSpt + relativePath;
        return absolutePath;
    }

    public static String connect(String... paths)
    {
        if (ArrayUtils.isEmpty(paths))
        {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String path : paths)
        {
            sb.append(path).append(fileSpt);
        }

        String connectedPath = sb.toString();

        // 去掉最后的分隔符
        connectedPath = StringUtils.removeEnd(connectedPath, fileSpt);

        // 将连续的分隔符合并，并改为当前平台的分隔符
        connectedPath = connectedPath.replaceAll("[\\\\/]{1,}", fileSpt + fileSpt);

        return connectedPath;
    }

    public static void main(String[] args)
    {
        String packagePath = PathUtil.getPackagePath();
        String relativePath = PathUtil.getRelativePath();
        String absolutePath = PathUtil.getAbsolutePath();

        System.out.println(packagePath);
        System.out.println(relativePath);
        System.out.println(absolutePath);
    }
}
