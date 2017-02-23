package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class RegexValidator extends BaseValidator
{
    private String regex;

    public RegexValidator(String regex)
    {
        this.regex = regex;
    }

    @Override
    public ValidateResult validate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return new ValidateResult(value, this, false, null);
        }

        if (!value.matches(regex))
        {
            return new ValidateResult(value, this, false, null);
        }

        return new ValidateResult(value, this, true, null);
    }
}
