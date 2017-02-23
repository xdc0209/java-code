package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class PortValidator extends BaseValidator
{
    private static String portRegexp = "^[1-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    @Override
    public ValidateResult validate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return new ValidateResult(value, this, false, null);
        }

        if (!value.matches(portRegexp))
        {
            return new ValidateResult(value, this, false, null);
        }

        return new ValidateResult(value, this, true, null);
    }
}
