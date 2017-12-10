package com.xdc.basic.api;

import java.util.HashMap;
import java.util.Map;

public class EnumTest
{
    public enum Color
    {
        RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);

        private final String name;
        private final int    index;

        // 注意构造函数要为私有，java枚举采用单例模式，无需外接调用构造方法
        private Color(String name, int index)
        {
            this.name = name;
            this.index = index;
        }

        // 不要实现set方法
        public String getName()
        {
            return name;
        }

        public int getIndex()
        {
            return index;
        }

        @Override
        public String toString()
        {
            return name;
        }

        // Implementing a fromString method on an enum type
        private static final Map<String, Color> stringToEnum = new HashMap<String, Color>();

        static
        {
            // Initialize map from constant name to enum constant
            for (Color color : values())
            {
                stringToEnum.put(color.toString(), color);
            }
        }

        // Returns Operation for string, or null if string is invalid
        public static Color fromString(String symbol)
        {
            return stringToEnum.get(symbol);
        }
    }

    public static void main(String[] args)
    {
        // 枚举可以用==比较
        Color color = Color.GREEN;

        String colorString = color.toString();

        Color color2 = Color.fromString(colorString);
        if (color2 == Color.GREEN)
        {
            System.out.println("颜色为:" + color);
        }
    }
}
