package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class PortValidator extends BaseValidator
{
    private static String portRegexp = "^[1-9]|[1-9][0-9]|[1-9][0-9]{2}|[1-9][0-9]{3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]$";

    @Override
    protected boolean doValidate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return false;
        }

        if (!value.matches(portRegexp))
        {
            return false;
        }

        return true;
    }

    @Override
    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = super.getToStringBuilder();
        toStringBuilder.append("portRegexp", portRegexp);
        return toStringBuilder;
    }
}
