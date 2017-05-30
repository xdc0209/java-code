package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.composite;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite.NotValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;

public class NotValidator extends BaseValidator
{
    private Validator validator;

    public NotValidator(Validator validator)
    {
        this.validator = validator;
    }

    public void setValidator(Validator validator)
    {
        this.validator = validator;
    }

    @Override
    public ValidateResult validate(String value)
    {
        ValidateResult validateResult = validator.validate(value);

        NotValidateResult notValidateResult = new NotValidateResult(value);
        notValidateResult.setValidateResult(validateResult);

        notValidateResult.setReslut(!ValidateResult.isPassed(validateResult));

        return notValidateResult;
    }
}
