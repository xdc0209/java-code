package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import java.util.ArrayList;
import java.util.List;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;

public class EnumStringValidator extends BaseValidator
{
    private List<String> enumStringList = new ArrayList<String>();

    public EnumStringValidator(String... enumStrings)
    {
        for (String enumString : enumStrings)
        {
            enumStringList.add(enumString);
        }
    }

    @Override
    public ValidateResult validate(String value)
    {
        if (enumStringList.contains(value))
        {
            return new ValidateResult(value, this, true, null);
        }
        else
        {
            return new ValidateResult(value, this, false, null);
        }
    }
}
