package com.xdc.basic.algorithm.other.alg5;

import java.security.SecureRandom;

/**
 * 蒙特卡罗方法
 * http://www.ruanyifeng.com/blog/2015/07/monte-carlo-method.html
 */
public class MonteCarloMethod
{
    /**
     * 使用正方形与圆的面积比计算PI。
     * 正方形：(0,0)、(1,0)、(1,1)、(0,1)。
     * 圆：圆心(0.5,0.5)、半径0.5。
     */
    public static void main(String[] args)
    {
        int pointInCircleCount = 0; // 在圆中的点
        int pointInSquareCount = 1000000; // 在正方形中的点

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < pointInSquareCount; i++)
        {
            double x = random.nextDouble(); // 在[0,1]之间随机生成点的x坐标
            double y = random.nextDouble(); // 在[0,1]之间随机生成点的y坐标

            // 计算点是否落在圆内
            if (Math.sqrt((x - 0.5) * (x - 0.5) + (y - 0.5) * (y - 0.5)) < 0.5)
            {
                pointInCircleCount++;
            }
        }

        // 圆的面积/正方形的面积=(PI*R*R)/(2*R*2*R)=PI/4 ==> PI=4*圆的面积/正方形的面积
        double PI = 4.0 * pointInCircleCount / pointInSquareCount;

        System.out.println("point_in_circle_count: " + pointInCircleCount);
        System.out.println("point_in_square_count: " + pointInSquareCount);
        System.out.println("PI: " + PI);
    }

    /**
     * 使用正方形与圆的面积比计算PI。
     * 正方形：(0,0)、(1,0)、(1,1)、(0,1)。
     * 四分之一圆：圆心(0,0)、半径1。
     */
    public static void main2(String[] args)
    {
        int pointInCircleCount = 0; // 在圆中的点
        int pointInSquareCount = 1000000; // 在正方形中的点

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < pointInSquareCount; i++)
        {
            double x = random.nextDouble(); // 在[0,1]之间随机生成点的x坐标
            double y = random.nextDouble(); // 在[0,1]之间随机生成点的y坐标

            // 计算点是否落在圆内
            if (Math.sqrt(x * x + y * y) < 1)
            {
                pointInCircleCount++;
            }
        }

        // 四分之一圆的面积/正方形的面积=(PI*R*R/4)/(R*R)=PI/4 ==> PI=4*圆的面积/正方形的面积
        double PI = 4.0 * pointInCircleCount / pointInSquareCount;

        System.out.println("point_in_circle_count: " + pointInCircleCount);
        System.out.println("point_in_square_count: " + pointInSquareCount);
        System.out.println("PI: " + PI);
    }
}
