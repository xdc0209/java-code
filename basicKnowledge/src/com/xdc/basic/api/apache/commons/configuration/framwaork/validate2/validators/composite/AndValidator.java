package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.composite;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite.AndValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;

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
    public ValidateResult validate(String value)
    {
        AndValidateResult andValidateResult = new AndValidateResult(value, null, null, null);

        for (Validator validator : validators)
        {
            ValidateResult validateResult = validator.validate(value);
            andValidateResult.addValidateResult(validateResult);

            if (!ValidateResult.isPassed(validateResult))
            {
                andValidateResult.setReslut(false);
                return andValidateResult;
            }
        }

        andValidateResult.setReslut(true);
        return andValidateResult;
    }
}
