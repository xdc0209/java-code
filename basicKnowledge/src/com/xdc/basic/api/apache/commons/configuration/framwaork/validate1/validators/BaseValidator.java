package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.log.slf4j.LogTest;

public abstract class BaseValidator implements Validator
{
    protected static Logger      log                       = LoggerFactory.getLogger(LogTest.class);

    // ThreadLocal可以实现在多线程下可以同时使用同一Validator对象
    private ThreadLocal<String>  valueThreadLocal          = new ThreadLocal<String>();
    private ThreadLocal<Boolean> validateReslutThreadLocal = new ThreadLocal<Boolean>();
    private ThreadLocal<String>  validateDetailThreadLocal = new ThreadLocal<String>();

    public String getValue()
    {
        return valueThreadLocal.get();
    }

    public void setValue(String value)
    {
        this.valueThreadLocal.set(value);
    }

    public Boolean getValidateReslut()
    {
        return validateReslutThreadLocal.get();
    }

    public void setValidateReslut(Boolean validateReslut)
    {
        this.validateReslutThreadLocal.set(validateReslut);
    }

    public String getValidateDetail()
    {
        return validateDetailThreadLocal.get();
    }

    public void setValidateDetail(String validateDetail)
    {
        this.validateDetailThreadLocal.set(validateDetail);
    }

    @Override
    public void reset()
    {
        setValue(null);
        setValidateReslut(null);
        setValidateDetail(null);
    }

    @Override
    public boolean validate(String value)
    {
        // 如果被使用过，重置校验器，以便重用
        if (getValue() != null)
        {
            reset();
        }

        setValue(value);

        try
        {
            if (doValidate(value))
            {
                setValidateReslut(true);
                return true;
            }
            else
            {
                setValidateReslut(false);
                return false;
            }
        }
        catch (Throwable e)
        {
            log.error(String.format("Validate [%s] failed.", value), e);
            setValidateReslut(false);
            setValidateDetail(e.getMessage());
            return false;
        }
    }

    protected abstract boolean doValidate(String value);

    @Override
    public String detail()
    {
        if (validateReslutThreadLocal.get() == null)
        {
            throw new IllegalStateException(
                    "Not invoke BaseValidator.validate(String value) yet. Please invoke it first.");
        }

        return toString();
    }

    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        toStringBuilder.append("value", getValue());
        toStringBuilder.append("validateReslut", getValidateReslut());
        toStringBuilder.append("validateDetail", getValidateDetail());
        return toStringBuilder;
    }

    @Override
    public String toString()
    {
        return getToStringBuilder().toString();
    }
}
