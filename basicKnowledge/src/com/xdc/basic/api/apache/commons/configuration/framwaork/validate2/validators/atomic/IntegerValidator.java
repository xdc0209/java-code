package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

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
    public ValidateResult validate(String value)
    {
        try
        {
            Integer valueInteger = Integer.parseInt(value);

            if (min != null && valueInteger < min)
            {
                return new ValidateResult(value, this, false, null);
            }

            if (max != null && valueInteger > max)
            {
                return new ValidateResult(value, this, false, null);
            }
        }
        catch (NumberFormatException e)
        {
            return new ValidateResult(value, this, false,
                    String.format("Value [%s] could not be parsed to Integer.", value));
        }

        return new ValidateResult(value, this, true, null);
    }
}
