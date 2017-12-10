package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class BooleanValidator extends BaseValidator
{
    @Override
    protected boolean doValidate(String value)
    {
        if (StringUtils.equals(value, "true") || StringUtils.equals(value, "false"))
        {
            return true;
        }

        return false;
    }
}
