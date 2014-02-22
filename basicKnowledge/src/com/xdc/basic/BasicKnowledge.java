package com.xdc.basic;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.SystemUtils;

public class BasicKnowledge
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        // 1. 获取环境变量
        System.getenv("PATH");
        System.getenv("JAVA_HOME");

        // 2. 获取系统属性
        Properties p = System.getProperties(); // 得到所有属性值
        p.list(System.out);
        System.getProperty("file.separator"); // 文件分隔符（在 UNIX 系统中是“/”）
        System.getProperty("path.separator"); // 路径分隔符（在 UNIX 系统中是“:”）
        System.getProperty("line.separator"); // 行分隔符（在 UNIX 系统中是“/n”）
        System.getProperty("user.name"); // 用户的账户名称
        System.getProperty("user.home"); // 用户的主目录
        System.getProperty("user.dir"); // 用户的当前工作目录
        // apache 类库
        String LINE_SEPARATOR = SystemUtils.LINE_SEPARATOR;
        System.out.println(LINE_SEPARATOR);

        // sun.jnu.encoding 影响文件名的创建、 cmd命令行的当前编码
        // file.encoding 影响到文件内容
        System.out.println("sun.jnu.encoding");
        System.out.println("file.encoding");

        // 3. 字节数据-->字符串
        byte[] bytes = new byte[100];
        String str = new String(bytes);

        // 4. StringBuffer(同步)和StringBuilder(非同步)
        //    如果是JDK1.5最好用StringBuilder取代StringBuffer, 除非有线程安全的要求.
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append("World");
        sb.reverse();// 反转字符串
        sb.toString();

        // 5. 数字
        Integer integer = 1;
        integer.intValue(); // 数字与对象之间互相转换 - Integer转int

        Integer.toBinaryString(15); // 整数 -> 二进制字符串
        Integer.toOctalString(15); // 整数 -> 八进制字符串
        Integer.toHexString(15); // 整数 -> 十六进制字符串

        Math.round(3.4); // // 浮点数的舍入,结果为3

        // 随机数
        Random r = new Random();
        r.nextDouble();
        r.nextInt();

        // 47（参数类型为long） 为随机数种子，如果种子不变，产生的随机数序列不变。无参数时，取系统时间做种子
        Random rand = new Random(47);
        // 产生0~25的随机值
        System.out.println(rand.nextInt(26));

        // 产生0和1之间的（包括0，但不包括1）的一个double值
        System.out.println(Math.random());

        // 6. 日期和时间
        Date today = new Date(); // 查看当前日期
        // System.out.println(today);

        // 按要求格式打印日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.format(today);

        // 记录耗时
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("用时：" + elapsed);

        // 获取Timestamp对象
        new Timestamp(System.currentTimeMillis());
    }
}
