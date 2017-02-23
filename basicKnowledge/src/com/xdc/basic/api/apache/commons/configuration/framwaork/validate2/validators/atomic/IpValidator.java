package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class IpValidator extends BaseValidator
{
    private static String ipRegexp = "^((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$";

    @Override
    public ValidateResult validate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return new ValidateResult(value, this, false, null);
        }

        if (!value.matches(ipRegexp))
        {
            return new ValidateResult(value, this, false, null);
        }

        return new ValidateResult(value, this, true, null);
    }
}
