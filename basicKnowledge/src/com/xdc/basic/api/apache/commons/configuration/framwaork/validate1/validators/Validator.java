package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators;

public interface Validator
{
    void reset();

    boolean validate(String value);

    String detail();
}
