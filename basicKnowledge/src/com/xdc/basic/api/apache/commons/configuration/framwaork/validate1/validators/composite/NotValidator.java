package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.composite;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.Validator;

public class NotValidator extends BaseValidator
{
    Validator validator;

    public NotValidator(Validator validator)
    {
        this.validator = validator;
    }

    public void reset()
    {
        super.reset();
        validator.reset();
    }

    @Override
    protected boolean doValidate(String value)
    {
        return !validator.validate(value);
    }

    @Override
    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = super.getToStringBuilder();
        toStringBuilder.append("validator", validator);
        return toStringBuilder;
    }
}
