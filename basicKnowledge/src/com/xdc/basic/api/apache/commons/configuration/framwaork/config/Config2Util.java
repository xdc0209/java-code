package com.xdc.basic.api.apache.commons.configuration.framwaork.config;

import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesConfigurationHolder;
import com.xdc.basic.skills.GetPath;

public class Config2Util
{
    private static final PropertiesConfigurationHolder propertiesConfigurationHolder = new PropertiesConfigurationHolder(
            GetPath.connect(GetPath.getAbsolutePath(), "../../PropertiesConfigurationTest2.properties"));

}
