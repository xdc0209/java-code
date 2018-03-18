package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.composite;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.Validator;

public class AndValidator extends BaseValidator
{
    private List<Validator> validators = new ArrayList<Validator>();

    public AndValidator(Validator... validators)
    {
        for (Validator validator : validators)
        {
            this.validators.add(validator);
        }
    }

    public void addValidator(Validator validator)
    {
        validators.add(validator);
    }

    @Override
    public void reset()
    {
        super.reset();
        for (Validator validator : validators)
        {
            validator.reset();
        }
    }

    @Override
    protected boolean doValidate(String value)
    {
        for (Validator validator : validators)
        {
            if (!validator.validate(value))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = super.getToStringBuilder();
        toStringBuilder.append("validators", validators);
        return toStringBuilder;
    }
}
