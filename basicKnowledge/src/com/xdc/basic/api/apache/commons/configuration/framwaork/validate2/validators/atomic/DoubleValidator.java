package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

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
    public ValidateResult validate(String value)
    {
        try
        {
            Double valueDouble = Double.parseDouble(value);

            if (min != null && valueDouble < min)
            {
                return new ValidateResult(value, this, false, null);
            }

            if (max != null && valueDouble > max)
            {
                return new ValidateResult(value, this, false, null);
            }
        }
        catch (NumberFormatException e)
        {
            return new ValidateResult(value, this, false,
                    String.format("Value [%s] could not be parsed to Double.", value));
        }

        return new ValidateResult(value, this, true, null);
    }
}
