package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class LongValidator extends BaseValidator
{
    private Long min = null;

    private Long max = null;

    public LongValidator()
    {
    }

    public LongValidator(Long min, Long max)
    {
        super();
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean doValidate(String value)
    {
        try
        {
            Long valueLong = Long.parseLong(value);

            if (min != null && valueLong < min)
            {
                return false;
            }

            if (max != null && valueLong > max)
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            setValidateDetail(String.format("Value [%s] could not be parsed to Long.", value));
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
