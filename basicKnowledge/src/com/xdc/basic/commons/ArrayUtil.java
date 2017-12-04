package com.xdc.basic.commons;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayUtil
{
    /**
     * 创建泛型数组。java是不支持范型数组的，《think in java》里面讲过，底层的数组类型只能是Object的，不过可以通过java反射来实现！
     * 由于使用反射创建泛型数组，性能不好，比原始写法慢几倍，高性能高频率场景不建议使用。
     * 
     * @param clazz
     *            泛型的Class。
     * @param length
     *            数组长度。
     * @return
     *         新数组。
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<T> clazz, int length)
    {
        return (T[]) Array.newInstance(clazz, length);
    }

    /**
     * 创建泛型数组，功能与com.xdc.basic.commons.ArrayUtil.newArray(Class<T>, int)相同，优点是自动泛型类型，不用特意传Class。
     * 由于使用反射创建泛型数组，性能不好，比原始写法慢几倍，高性能高频率场景不建议使用。
     * 
     * @param length
     *            数组长度。
     * @param array
     *            此参数不用填，使用java中的可变参数，实现类型的自动识别。如果填写的话，会复制里面的内容到新数组。
     * @return
     *         新数组。
     */
    @SafeVarargs
    public static <T> T[] newArray(int length, T... array)
    {
        return Arrays.copyOf(array, length);
    }

    /**
     * 复制数组。
     * 
     * @param original
     *            原始数组。
     * @param newLength
     *            新数组长度。
     * @return
     *         新数组。
     */
    public static <T> T[] copy(T[] original, int newLength)
    {
        return Arrays.copyOf(original, newLength);
    }
}
