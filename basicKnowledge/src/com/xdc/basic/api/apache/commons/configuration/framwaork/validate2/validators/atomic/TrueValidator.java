package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class TrueValidator extends BaseValidator
{
    @Override
    public ValidateResult validate(String value)
    {
        return new ValidateResult(value, this, true, null);
    }
}
