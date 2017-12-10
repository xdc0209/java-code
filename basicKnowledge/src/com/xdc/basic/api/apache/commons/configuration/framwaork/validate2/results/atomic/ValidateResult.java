package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;

public class ValidateResult
{
    private String    value;

    private Validator validator;

    private Boolean   reslut;

    private String    detail;

    public ValidateResult(String value, Validator validator, Boolean reslut, String detail)
    {
        this.value = value;
        this.validator = validator;
        this.reslut = reslut;
        this.detail = detail;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Validator getValidator()
    {
        return validator;
    }

    public void setValidator(Validator validator)
    {
        this.validator = validator;
    }

    public Boolean getReslut()
    {
        return reslut;
    }

    public void setReslut(Boolean reslut)
    {
        this.reslut = reslut;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static boolean isPassed(ValidateResult validateResult)
    {
        if (validateResult == null || validateResult.getReslut() == null)
        {
            return false;
        }

        return validateResult.getReslut();
    }
}
