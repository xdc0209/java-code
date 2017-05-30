package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;

public class OrValidateResult extends ValidateResult
{
    private List<ValidateResult> validateResults = new ArrayList<ValidateResult>();

    public OrValidateResult(String value)
    {
        super(value, null, false, null);
    }

    public void addValidateResult(ValidateResult validateResult)
    {
        validateResults.add(validateResult);
    }
}
