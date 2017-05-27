package com.xdc.basic.skills;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

// 判断两个Integer值相等最好不用==最好使用equals
public class IntegerEqualsTest
{
    public static void main(String[] args) throws IOException
    {
        int a = 3;
        int b = 3;
        Integer c = 3;
        Integer d = 3;

        int e = 321;
        int f = 321;
        Integer g = 321;
        Integer h = 321;

        System.out.println("==");
        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(c == d);
        System.out.println(d == a);
        System.out.println();

        System.out.println(e == f);
        System.out.println(f == g);
        System.out.println(g == h); // 注意，这就是坑。java基于性能考虑，对-128到127做了缓存，判断两个Integer值相等最好不用==最好使用equals。参见：http://blog.csdn.net/u012768459/article/details/52923748
        System.out.println(h == e);
        System.out.println();

        // 可以使用
        System.out.println("Objects.equals()");
        System.out.println(Objects.equals(a, b));
        System.out.println(Objects.equals(b, c));
        System.out.println(Objects.equals(c, d));
        System.out.println(Objects.equals(d, a));
        System.out.println();

        System.out.println(Objects.equals(e, f));
        System.out.println(Objects.equals(f, g));
        System.out.println(Objects.equals(g, h));
        System.out.println(Objects.equals(h, e));
        System.out.println();

        // 或使用
        System.out.println("ObjectUtils.equals()");
        System.out.println(ObjectUtils.equals(a, b));
        System.out.println(ObjectUtils.equals(b, c));
        System.out.println(ObjectUtils.equals(c, d));
        System.out.println(ObjectUtils.equals(d, a));
        System.out.println();

        System.out.println(ObjectUtils.equals(e, f));
        System.out.println(ObjectUtils.equals(f, g));
        System.out.println(ObjectUtils.equals(g, h));
        System.out.println(ObjectUtils.equals(h, e));
        System.out.println();

        System.out.println("<"); // 除了相等，判断大于小于是可以愉快的使用<,>。这个问题主要是java中==对于对象的比较是比是内存地址，对于基本类型的比较是比的值大小。这两用途容易搞混，遇到此坑。
        System.out.println(a < b);
        System.out.println(a < d);
        System.out.println(c < b);
        System.out.println(c < d);

        System.out.println(a < f);
        System.out.println(a < h);
        System.out.println(c < f);
        System.out.println(c < h);
    }
}
