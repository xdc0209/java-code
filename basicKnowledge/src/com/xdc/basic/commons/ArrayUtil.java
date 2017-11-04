package com.xdc.basic.commons;

import java.lang.reflect.Array;

public class ArrayUtil
{
    /**
     * java是不支持范型数组的，《think in java》里面讲过，底层的数组类型只能是Object的，不过可以通过java反射来实现！
     * 由于使用反射创建泛型数组，性能不好，比原始写法慢几倍，高性能高频率场景不建议使用。
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<T> clazz, int length)
    {
        return (T[]) Array.newInstance(clazz, length);
    }
}
