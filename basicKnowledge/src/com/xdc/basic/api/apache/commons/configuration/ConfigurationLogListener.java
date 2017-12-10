package com.xdc.basic.api.apache.commons.configuration;

import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConfigurationLogListener implements ConfigurationListener
{
    // 常量列表，蛋疼的api
    // PropertiesConfiguration.EVENT_ADD_PROPERTY 1
    // PropertiesConfiguration.EVENT_CLEAR_PROPERTY 2
    // PropertiesConfiguration.EVENT_SET_PROPERTY 3
    // PropertiesConfiguration.EVENT_CLEAR 4
    // PropertiesConfiguration.EVENT_READ_PROPERTY 5
    // PropertiesConfiguration.EVENT_RELOAD 20
    // PropertiesConfiguration.EVENT_CONFIG_CHANGED 21

    @Override
    public void configurationChanged(ConfigurationEvent event)
    {
        // only display events after the modification was done
        System.out.println(
                "Received event! " + ToStringBuilder.reflectionToString(event, ToStringStyle.MULTI_LINE_STYLE));
    }
}
