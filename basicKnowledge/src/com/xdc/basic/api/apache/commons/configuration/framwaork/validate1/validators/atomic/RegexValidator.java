package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

public class RegexValidator extends BaseValidator
{
    private String regex;

    public RegexValidator(String regex)
    {
        this.regex = regex;
    }

    @Override
    protected boolean doValidate(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return false;
        }

        if (!value.matches(regex))
        {
            return false;
        }

        return true;
    }

    @Override
    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = super.getToStringBuilder();
        toStringBuilder.append("regex", regex);
        return toStringBuilder;
    }
}
