package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;

public class NotValidateResult extends ValidateResult
{
    @SuppressWarnings("unused")
    private ValidateResult validateResult;

    public NotValidateResult(String value, Validator validator, Boolean reslut, String detail)
    {
        super(value, validator, reslut, detail);
    }

    public void setValidateResult(ValidateResult validateResult)
    {
        this.validateResult = validateResult;
    }
}
