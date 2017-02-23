package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;

public class AndValidateResult extends ValidateResult
{
    private List<ValidateResult> validateResults = new ArrayList<ValidateResult>();

    public AndValidateResult(String value, Validator validator, Boolean reslut, String detail)
    {
        super(value, validator, reslut, detail);
    }

    public void addValidateResult(ValidateResult validateResult)
    {
        validateResults.add(validateResult);
    }
}
