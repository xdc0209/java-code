package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.Validator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.composite.AndValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.composite.NotValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.composite.OrValidator;

public class Validators
{
    public static AndValidator and(Validator... validators)
    {
        return new AndValidator(validators);
    }

    public static OrValidator or(Validator... validators)
    {
        return new OrValidator(validators);
    }

    public static NotValidator not(Validator validator)
    {
        return new NotValidator(validator);
    }
}
