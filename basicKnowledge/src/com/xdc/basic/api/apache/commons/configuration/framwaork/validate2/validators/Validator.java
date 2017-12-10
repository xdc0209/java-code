package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;

public interface Validator
{
    ValidateResult validate(String value);
}
