package com.xdc.basic.commons.to.string.style;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 增强ToStringStyle：
 * （1）支持解码字节数组成字符串。
 * （2）支持过滤map中的某些key。
 * （3）支持递归反射子对象。
 * 
 * 参考自：org.apache.commons.lang3.builder.ToStringStyle
 */
public class EnhanceToStringStyle extends ToStringStyle
{
    private static final long serialVersionUID             = 7649450962873124324L;

    /**
     * 支持解码字节数组成字符串。
     */
    private Charset           bytesToStringCharset         = Charset.forName("UTF-8");

    /**
     * 支持过滤map中的某些key。
     */
    private Object[]          excludeMapKeys               = new Object[] {};

    /**
     * 支持递归反射子对象。
     */
    private boolean           recursiveReflectionSubobject = true;

    private EnhanceToStringStyle()
    {
        super();
    }

    public static EnhanceToStringStyle newDefaultToStringStyle()
    {
        EnhanceToStringStyle enhanceToStringStyle = new EnhanceToStringStyle();

        return enhanceToStringStyle;
    }

    public static EnhanceToStringStyle newMultiLineToStringStyle()
    {
        EnhanceToStringStyle enhanceToStringStyle = new EnhanceToStringStyle();

        enhanceToStringStyle.setContentStart("[");
        enhanceToStringStyle.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
        enhanceToStringStyle.setFieldSeparatorAtStart(true);
        enhanceToStringStyle.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");

        return enhanceToStringStyle;
    }

    public static EnhanceToStringStyle newNoFieldNameToStringStyle()
    {
        EnhanceToStringStyle enhanceToStringStyle = new EnhanceToStringStyle();

        enhanceToStringStyle.setUseFieldNames(false);

        return enhanceToStringStyle;
    }

    public static EnhanceToStringStyle newShortPrefixToStringStyle()
    {
        EnhanceToStringStyle enhanceToStringStyle = new EnhanceToStringStyle();

        enhanceToStringStyle.setUseShortClassName(true);
        enhanceToStringStyle.setUseIdentityHashCode(false);

        return enhanceToStringStyle;
    }

    public static EnhanceToStringStyle newSimpleToStringStyle()
    {
        EnhanceToStringStyle enhanceToStringStyle = new EnhanceToStringStyle();

        enhanceToStringStyle.setUseClassName(false);
        enhanceToStringStyle.setUseIdentityHashCode(false);
        enhanceToStringStyle.setUseFieldNames(false);
        enhanceToStringStyle.setContentStart("");
        enhanceToStringStyle.setContentEnd("");

        return enhanceToStringStyle;
    }

    public EnhanceToStringStyle configBytesToStringCharset(Charset bytesToStringCharset)
    {
        this.bytesToStringCharset = bytesToStringCharset;
        return this;
    }

    public EnhanceToStringStyle configExcludeMapKeys(Object... excludeMapKeys)
    {
        this.excludeMapKeys = (excludeMapKeys == null ? new Object[] {} : (Object[]) excludeMapKeys.clone());
        return this;
    }

    public EnhanceToStringStyle configRecursiveReflectionSubobject(boolean recursiveReflectionSubobject)
    {
        this.recursiveReflectionSubobject = recursiveReflectionSubobject;
        return this;
    }

    /**
     * 处理字节数组。
     */
    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array)
    {
        if (bytesToStringCharset == null)
        {
            super.appendDetail(buffer, fieldName, array);
        }
        else
        {
            buffer.append("Byte[");
            buffer.append(new String(array, bytesToStringCharset));
            buffer.append("]");
        }
    }

    /**
     * 处理对象。
     */
    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object value)
    {
        // 如果不递归反射，则直接调用父类的方法。
        if (!recursiveReflectionSubobject)
        {
            super.appendDetail(buffer, fieldName, value);
        }

        // 如果是基本类型，直接附加。
        else if (value instanceof Number || value instanceof Boolean || value instanceof Character
                || value instanceof CharSequence)
        {
            buffer.append(value);
        }

        // 如果是复杂对象，通过反射获取字符串。
        else
        {
            buffer.append(ToStringBuilder.reflectionToString(value, this));
        }
    }

    /**
     * 处理集合。
     * 
     * 参考自：java.util.AbstractCollection.toString()
     */
    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> collection)
    {
        buffer.append("[");

        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext())
        {
            Object object = iterator.next();
            if (object == null)
            {
                appendNullText(buffer, fieldName);
            }
            else if (object == collection)
            {
                // 避免自身引用，出现死循环。
                buffer.append("(this Collection)");
            }
            else
            {
                appendInternal(buffer, fieldName, object, true);
            }

            if (iterator.hasNext())
            {
                buffer.append(",").append(" ");
            }
        }

        buffer.append("]");
    }

    /**
     * 处理映射。
     * 
     * 参考自：java.util.AbstractMap.toString()
     */
    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map)
    {
        buffer.append("{");

        Iterator<?> iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            Object key = iterator.next();
            Object value = map.get(key);

            // 过滤map中的某些key。
            if (ArrayUtils.contains(excludeMapKeys, key))
            {
                continue;
            }

            if (key == null)
            {
                appendNullText(buffer, fieldName);
            }
            else if (key == map)
            {
                // 避免自身引用，出现死循环。
                buffer.append("(this Map)");
            }
            else
            {
                appendInternal(buffer, fieldName, key, true);
            }

            buffer.append("=");

            if (value == null)
            {
                appendNullText(buffer, fieldName);
            }
            else if (value == map)
            {
                // 避免自身引用，出现死循环。
                buffer.append("(this Map)");
            }
            else
            {
                appendInternal(buffer, fieldName, value, true);
            }

            if (iterator.hasNext())
            {
                buffer.append(",").append(" ");
            }
        }

        buffer.append("}");
    }
}
