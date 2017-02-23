package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class NotEmptyStringValidator extends BaseValidator
{
    @Override
    public boolean doValidate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return false;
        }

        return true;
    }
}
