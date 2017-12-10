package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.BaseValidator;
import com.xdc.basic.commons.network.IpUtil;

public class PortValidator extends BaseValidator
{
    @Override
    public ValidateResult validate(String value)
    {
        if (IpUtil.isIpv4Port(value))
        {
            return new ValidateResult(value, this, true, null);
        }
        else
        {
            return new ValidateResult(value, this, false, null);
        }
    }
}
