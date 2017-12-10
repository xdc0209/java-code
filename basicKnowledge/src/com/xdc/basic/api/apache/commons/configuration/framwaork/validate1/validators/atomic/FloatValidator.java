package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class FloatValidator extends BaseValidator
{
    private Float min = null;

    private Float max = null;

    public FloatValidator()
    {
    }

    public FloatValidator(Float min, Float max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean doValidate(String value)
    {
        try
        {
            Float valueFloat = Float.parseFloat(value);

            if (min != null && valueFloat < min)
            {
                return false;
            }

            if (max != null && valueFloat > max)
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            setValidateDetail(String.format("Value [%s] could not be parsed to Float.", value));
            return false;
        }

        return true;
    }

    @Override
    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = super.getToStringBuilder();
        toStringBuilder.append("min", min);
        toStringBuilder.append("max", max);
        return toStringBuilder;
    }
}
