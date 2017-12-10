package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class DoubleValidator extends BaseValidator
{
    private Double min = null;

    private Double max = null;

    public DoubleValidator()
    {
    }

    public DoubleValidator(Double min, Double max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean doValidate(String value)
    {
        try
        {
            Double valueDouble = Double.parseDouble(value);

            if (min != null && valueDouble < min)
            {
                return false;
            }

            if (max != null && valueDouble > max)
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            setValidateDetail(String.format("Value [%s] could not be parsed to Double.", value));
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
