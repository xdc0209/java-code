package com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.BaseValidator;
import com.xdc.basic.commons.network.IpUtil;

public class IpWithPortValidator extends BaseValidator
{
    @Override
    public boolean doValidate(String value)
    {
        return IpUtil.isIpv4IpPort(value);
    }
}
