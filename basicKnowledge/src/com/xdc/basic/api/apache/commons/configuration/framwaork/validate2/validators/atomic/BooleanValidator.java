package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class BooleanValidator extends BaseValidator
{
    @Override
    public ValidateResult validate(String value)
    {
        if (StringUtils.equals(value, "true") || StringUtils.equals(value, "false"))
        {
            return new ValidateResult(value, this, true, null);
        }
        else
        {
            return new ValidateResult(value, this, false, null);
        }
    }
}
