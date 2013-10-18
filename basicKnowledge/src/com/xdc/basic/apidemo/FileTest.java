package com.xdc.basic.apidemo;

import java.io.File;
import java.io.IOException;

public class FileTest
{
    public static void main(String[] args) throws IOException
    {
        File f = new File("xdc.txt");
        // 获取文件信息
        f.exists(); // 如果文件存在，返回true
        f.getCanonicalPath(); // 获取全名
        f.getName(); // 文件名
        f.getParent(); // 父目录
        f.canRead(); // 如果文件可读，返回true
        f.canWrite(); // 如果文件可写，返回true
        f.lastModified(); // 文件更新时间
        f.length(); // 文件大小
        f.isFile(); // 如果是文件，返回true
        f.isDirectory(); // 如果是目录，返回true

        f.createNewFile(); // 创建
        f.delete(); // 文件

        // 创建新目录
        new File("/home/ian/bin").mkdir(); // 如果"/home/ian"存在，则可以创建bin目录
        new File("/home/ian/bin").mkdirs(); // 如果"/home/ian"不存在，会创建所有的目录

        // 临时文件
        File dir = new File("C:\\test"); // 指定一个文件夹
        // 在test文件夹中创建foo前缀，tmp后缀的临时文件
        File tmp = File.createTempFile("foo", "tmp", dir);
        tmp.deleteOnExit(); // 在程序结束时删除该临时文件
    }
}
