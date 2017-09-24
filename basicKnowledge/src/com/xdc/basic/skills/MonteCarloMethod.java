package com.xdc.basic.skills;

import java.security.SecureRandom;

/**
 * 蒙特卡罗方法
 * http://www.ruanyifeng.com/blog/2015/07/monte-carlo-method.html
 */
public class MonteCarloMethod
{
    public static void main(String[] args)
    {
        int point_in_circle_count = 0; // 在圆中的点
        int point_in_square_count = 100000; // 在正方形中的点

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < point_in_square_count; i++)
        {
            double x = random.nextDouble(); // 在[0,1]之间随机生成点的x坐标
            double y = random.nextDouble(); // 在[0,1]之间随机生成点的y坐标

            // 计算点是否落在圆内
            if (Math.sqrt((x - 0.5) * (x - 0.5) + (y - 0.5) * (y - 0.5)) < 0.5)
            {
                point_in_circle_count++;
            }
        }

        // 圆的面积/正方形的面积=(PI*R*R)/(2*R*2*R)=PI/4 ==> PI=4*圆的面积/正方形的面积
        double PI = 4.0 * point_in_circle_count / point_in_square_count;

        System.out.println("point_in_circle_count: " + point_in_circle_count);
        System.out.println("point_in_square_count: " + point_in_square_count);
        System.out.println("PI: " + PI);
    }
}
