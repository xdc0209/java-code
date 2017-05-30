package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;

public class AndValidateResult extends ValidateResult
{
    private List<ValidateResult> validateResults = new ArrayList<ValidateResult>();

    public AndValidateResult(String value)
    {
        super(value, null, true, null);
    }

    public void addValidateResult(ValidateResult validateResult)
    {
        validateResults.add(validateResult);
    }
}
