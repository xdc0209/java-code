package com.xdc.basic.api.apache.commons.configuration.framwaork.config;

import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesConfigurationHolder;
import com.xdc.basic.api.apache.commons.configuration.framwaork.core.Property;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic.IpValidator;
import com.xdc.basic.skills.GetPath;

public class Config1Util
{
    private static final PropertiesConfigurationHolder propertiesConfigurationHolder = new PropertiesConfigurationHolder(
            GetPath.connect(GetPath.getAbsolutePath(), "../../PropertiesConfigurationTest1.properties"));

    public static Property<Integer>                    KEY1                          = new Property<Integer>(
            propertiesConfigurationHolder, "key1", "200", null, Integer.class);

    public static Property<String>                     APP_IP                        = new Property<String>(
            propertiesConfigurationHolder, "key2", "128.128.128.128", new IpValidator(), String.class);
}
