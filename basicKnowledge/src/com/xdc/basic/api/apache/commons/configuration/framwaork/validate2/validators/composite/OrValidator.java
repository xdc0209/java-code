package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.composite;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite.OrValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;

public class OrValidator extends BaseValidator
{
    private List<Validator> validators = new ArrayList<Validator>();

    public OrValidator(Validator... validators)
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
        OrValidateResult orValidateResult = new OrValidateResult(value, null, null, null);

        for (Validator validator : validators)
        {
            ValidateResult validateResult = validator.validate(value);
            orValidateResult.addValidateResult(validateResult);

            if (ValidateResult.isPassed(validateResult))
            {
                orValidateResult.setReslut(true);
                return orValidateResult;
            }
        }

        orValidateResult.setReslut(false);
        return orValidateResult;
    }
}
