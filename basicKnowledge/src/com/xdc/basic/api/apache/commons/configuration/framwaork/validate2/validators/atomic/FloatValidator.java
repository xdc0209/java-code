package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

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
    public ValidateResult validate(String value)
    {
        try
        {
            Float valueFloat = Float.parseFloat(value);

            if (min != null && valueFloat < min)
            {
                return new ValidateResult(value, this, false, null);
            }

            if (max != null && valueFloat > max)
            {
                return new ValidateResult(value, this, false, null);
            }
        }
        catch (NumberFormatException e)
        {
            return new ValidateResult(value, this, false,
                    String.format("Value [%s] could not be parsed to Float.", value));
        }

        return new ValidateResult(value, this, true, null);
    }
}
