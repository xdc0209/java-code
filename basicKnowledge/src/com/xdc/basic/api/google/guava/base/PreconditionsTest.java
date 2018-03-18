package com.xdc.basic.api.google.guava.base;

import com.google.common.base.Preconditions;

/**
 * 前置条件：让方法调用的前置条件判断更简单。
 * 
 * 参考：http://ifeve.com/google-guava-preconditions
 * 参考：https://github.com/google/guava/wiki/PreconditionsExplained
 * 参考：http://outofmemory.cn/java/guava/base/Preconditions
 */
public class PreconditionsTest
{
    public static void main(String[] args)
    {
        // google guava的base包中提供的Preconditions类用来方便的做参数的校验，他主要提供如下方法：
        // 1. checkArgument接受一个boolean类型的参数和一个可选的errorMsg参数，这个方法用来判断参数是否符合某种条件，符合什么条件google guava不关心，在不符合条件时会抛出IllegalArgumentException异常。
        // 2. checkState和checkArgument参数和实现基本相同，从字面意思上我们也可以知道这个方法是用来判断状态是否正确的，如果状态不正确会抛出IllegalStateException异常。
        // 3. checkNotNull方法用来判断参数是否不是null，如果为null则会抛出NullPointerException空指针异常。
        // 4. checkElementIndex方法用来判断用户传入的数组下标或者list索引位置，是否是合法的，如果不合法会抛出IndexOutOfBoundsException。
        // 5. checkPositionIndexes方法的作用和checkElementIndex方法相似，只是此方法的索引范围是从0到size包括size，而上面的方法不包括size。

        // Preconditions.checkArgument(false, "errorMessageTemplate", "errorMessageArgs"); // 检查boolean是否为true，传入false抛出异常IllegalArgumentException。
        // Preconditions.checkNotNull(null, "errorMessageTemplate", "errorMessageArgs"); // 检查<T> value为null直接抛出异常NullPointerException，否则直接返回value。
        // Preconditions.checkState(false, "errorMessageTemplate", "errorMessageArgs"); // 用来检查对象的某些状态。检查boolean是否为true，传入false抛出异常IllegalStateException。
        // Preconditions.checkElementIndex(10, 10); // 检查index作为索引值对某个列表、字符串或数组是否有效。index>=0 && index<size，否则抛异常IndexOutOfBoundsException。
        // Preconditions.checkPositionIndex(10, 10); // 检查index作为位置值对某个列表、字符串或数组是否有效。index>=0 && index<=size，否则抛异常IndexOutOfBoundsException。
        // Preconditions.checkPositionIndexes(10, 10, 10); // 检查[start, end]表示的位置范围对某个列表、字符串或数组是否有效。0<=start<=end<=size，否则抛异常IndexOutOfBoundsException。

        PreconditionsTest preconditionsDemo = new PreconditionsTest();
        preconditionsDemo.doSomething("Jim", 19, "hello world, hello java");
    }

    public void doSomething(String name, int age, String desc)
    {
        Preconditions.checkNotNull(name, "Name may not be null.");
        Preconditions.checkArgument(age >= 18 && age < 99, "Age must in range [18,99].");
        Preconditions.checkArgument(desc != null && desc.length() < 10, "Desc is too long, max length is %s.", 10);

        // do things.
    }
}
