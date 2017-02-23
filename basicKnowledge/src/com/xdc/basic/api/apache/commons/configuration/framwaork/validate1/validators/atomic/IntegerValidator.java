package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class IntegerValidator extends BaseValidator
{
    private Integer min = null;

    private Integer max = null;

    public IntegerValidator()
    {
    }

    public IntegerValidator(Integer min, Integer max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean doValidate(String value)
    {
        try
        {
            Integer valueInteger = Integer.parseInt(value);

            if (min != null && valueInteger < min)
            {
                return false;
            }

            if (max != null && valueInteger > max)
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            setValidateDetail(String.format("Value [%s] could not be parsed to Integer.", value));
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
