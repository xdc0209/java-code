package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;

public class NotValidateResult extends ValidateResult
{
    @SuppressWarnings("unused")
    private ValidateResult validateResult;

    public NotValidateResult(String value)
    {
        super(value, null, null, null);
    }

    public void setValidateResult(ValidateResult validateResult)
    {
        this.validateResult = validateResult;
    }
}
