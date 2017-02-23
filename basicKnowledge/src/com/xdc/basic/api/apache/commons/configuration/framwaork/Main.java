package com.xdc.basic.api.apache.commons.configuration.framwaork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.apache.commons.configuration.framwaork.config.Config1Util;
import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesConfigurationHolder;
import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesConfigurationListener;

public class Main
{
    private static Logger logger = LoggerFactory.getLogger(PropertiesConfigurationHolder.class);

    public static void main(String[] args) throws InterruptedException
    {
        int key1Value = Config1Util.KEY1.getValue();
        System.out.println(key1Value);

        Config1Util.KEY1.listen(new PropertiesConfigurationListener()
        {
            @Override
            public void configurationChanged(String propertiesFilePath, String key, String oldValue, String newValue)
            {
                logger.debug("The properties configuration [{}] is changed. Key=[{}], OldValue=[{}], NewValue=[{}].",
                        propertiesFilePath, key, oldValue, newValue);
            }
        }, key1Value);

        Config1Util.KEY1.setValue(400);
        System.out.println(Config1Util.KEY1.getValue());

        Config1Util.KEY1.setValue(key1Value);
        System.out.println(Config1Util.KEY1.getValue());

        String value = Config1Util.APP_IP.getValue();
        System.out.println(value);
    }
}
