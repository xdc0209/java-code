package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

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
    public ValidateResult validate(String value)
    {
        try
        {
            Long valueLong = Long.parseLong(value);

            if (min != null && valueLong < min)
            {
                return new ValidateResult(value, this, false, null);
            }

            if (max != null && valueLong > max)
            {
                return new ValidateResult(value, this, false, null);
            }
        }
        catch (NumberFormatException e)
        {
            return new ValidateResult(value, this, false,
                    String.format("Value [%s] could not be parsed to Long.", value));
        }

        return new ValidateResult(value, this, true, null);
    }
}
