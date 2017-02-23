package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class IpWithPortValidator extends BaseValidator
{
    private static String ipRegexp     = "^((?:(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9]))\\.){3}(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])$";

    private static String portRegexp   = "^[1-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    private static String ipPortRegexp = String.format("^(%s):(%s)$", StringUtils.substringBetween(ipRegexp, "^", "$"),
            StringUtils.substringBetween(portRegexp, "^", "$"));

    @Override
    public ValidateResult validate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return new ValidateResult(value, this, false, null);
        }

        if (!value.matches(ipPortRegexp))
        {
            return new ValidateResult(value, this, false, null);
        }

        return new ValidateResult(value, this, true, null);
    }
}
