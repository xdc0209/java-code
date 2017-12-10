package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;

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
    public boolean doValidate(String value)
    {
        if (enumStringList.contains(value))
        {
            return true;
        }

        return false;
    }

    @Override
    protected ToStringBuilder getToStringBuilder()
    {
        ToStringBuilder toStringBuilder = super.getToStringBuilder();
        toStringBuilder.append("enumStringList", enumStringList);
        return toStringBuilder;
    }
}
