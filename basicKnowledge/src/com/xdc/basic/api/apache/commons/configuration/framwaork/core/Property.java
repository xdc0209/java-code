package com.xdc.basic.api.apache.commons.configuration.framwaork.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.BooleanValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.DoubleValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.FloatValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.IntegerValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.LongValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.NotEmptyStringValidator;

public class Property<T>
{
    private static Logger    logger = LoggerFactory.getLogger(PropertiesHolder.class);

    private PropertiesHolder propertiesHolder;

    private String           key;

    // 旧值：在内存中记录旧值，当配置文件中的值不合法时，使用此值
    private volatile String  oldValue;

    // 默认值：配置文件中的值不合法时，使用旧值，旧值也不合法时，使用默认值。因为此值写死在代码中，不涉及用户修改，不做校验，由编码人员保证合法。
    private String           defaultValue;

    private Validator        validator;

    private Class<T>         valueClass;

    // 因为目前没有发现较好的方式获取到泛型的具体类型，所以暂时通过参数指定。
    public Property(PropertiesHolder propertiesHolder, String key, String defaultValue, Validator validator,
            Class<T> valueClass)
    {
        this.propertiesHolder = propertiesHolder;
        this.key = key;
        this.defaultValue = defaultValue;
        this.validator = validator;

        if (valueClass == null)
        {
            String error = String.format(
                    "Unsupport type:[%s] for valueClass. Support type:[String.class, Boolean.class, Integer.class, Long.class, Float.class, Double.class].",
                    valueClass);
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

        // 字符串
        if (String.class.isAssignableFrom(valueClass))
        {
            if (this.validator == null)
            {
                this.validator = new NotEmptyStringValidator();
            }
        }

        // 布尔型
        else if (Boolean.class.isAssignableFrom(valueClass))
        {
            if (this.validator == null)
            {
                this.validator = new BooleanValidator();
            }
        }

        // 整型
        else if (Integer.class.isAssignableFrom(valueClass))
        {
            if (this.validator == null)
            {
                this.validator = new IntegerValidator();
            }
        }
        else if (Long.class.isAssignableFrom(valueClass))
        {
            if (this.validator == null)
            {
                this.validator = new LongValidator();
            }
        }

        // 浮点型
        else if (Float.class.isAssignableFrom(valueClass))
        {
            if (this.validator == null)
            {
                this.validator = new FloatValidator();
            }
        }
        else if (Double.class.isAssignableFrom(valueClass))
        {
            if (this.validator == null)
            {
                this.validator = new DoubleValidator();
            }
        }
        else
        {
            String error = String.format(
                    "Unsupport type:[%s] for valueClass. Support type:[String.class, Boolean.class, Integer.class, Long.class, Float.class, Double.class].",
                    valueClass);
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

        this.valueClass = valueClass;
    }

    public String getKey()
    {
        return key;
    }

    @SuppressWarnings("unchecked")
    public T getValue()
    {
        // 考虑到常见场景和效率，本框架只支持String，Boolean，Integer，Long，Float，Double
        //
        // PropertiesConfiguration支持的类型：
        // PropertiesConfiguration.getString()
        //
        // PropertiesConfiguration.getBoolean()
        //
        // PropertiesConfiguration.getByte()
        // PropertiesConfiguration.getShort()
        // PropertiesConfiguration.getInteger()
        // PropertiesConfiguration.getLong()
        //
        // PropertiesConfiguration.getFloat()
        // PropertiesConfiguration.getDouble()
        //
        // PropertiesConfiguration.getBigDecimal()
        // PropertiesConfiguration.getBigInteger()

        String newValue = propertiesHolder.getString(key, oldValue, defaultValue, validator);
        oldValue = newValue;

        // 字符串
        if (String.class.isAssignableFrom(valueClass))
        {
            return (T) newValue;
        }

        // 布尔型
        else if (Boolean.class.isAssignableFrom(valueClass))
        {
            return (T) Boolean.valueOf(newValue);
        }

        // 整型
        else if (Integer.class.isAssignableFrom(valueClass))
        {
            return (T) Integer.valueOf(newValue);
        }
        else if (Long.class.isAssignableFrom(valueClass))
        {
            return (T) Long.valueOf(newValue);
        }

        // 浮点型
        else if (Float.class.isAssignableFrom(valueClass))
        {
            return (T) Float.valueOf(newValue);
        }
        else if (Double.class.isAssignableFrom(valueClass))
        {
            return (T) Double.valueOf(newValue);
        }

        // 不支持的类型
        else
        {
            // 不可能发生，构造方法中已校验valueClass的类型
            return null;
        }
    }

    public void setValue(T newValue)
    {
        propertiesHolder.setString(key, String.valueOf(newValue), validator);
        oldValue = String.valueOf(newValue);
    }

    public void listen(PropertiesListener propertiesListener)
    {
        listen(propertiesListener, getValue());
    }

    public void listen(PropertiesListener propertiesListener, T oldValue)
    {
        propertiesHolder.addListener(key, String.valueOf(oldValue), defaultValue, validator, propertiesListener);
    }
}