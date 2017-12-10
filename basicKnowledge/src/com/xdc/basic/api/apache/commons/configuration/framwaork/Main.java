package com.xdc.basic.api.apache.commons.configuration.framwaork;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.apache.commons.configuration.framwaork.config.Module1Config;
import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesHolder;
import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesListener;

public class Main
{
    private static Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);

    public static void main(String[] args) throws InterruptedException
    {
        int key1Value = Module1Config.KEY1.getValue();
        System.out.println(key1Value);

        Module1Config.KEY1.listen(new PropertiesListener()
        {
            @Override
            public void configurationChanged(String propertiesFilePath, String key, String oldValue, String newValue)
            {
                logger.debug("The properties file [{}] is changed. key=[{}], oldValue=[{}], newValue=[{}].",
                        FilenameUtils.getName(propertiesFilePath), key, oldValue, newValue);
            }
        }, key1Value);

        Module1Config.KEY1.setValue(400);
        System.out.println(Module1Config.KEY1.getValue());

        Module1Config.KEY1.setValue(key1Value);
        System.out.println(Module1Config.KEY1.getValue());

        String serverIp = Module1Config.SERVER_IP.getValue();
        String serverPort = Module1Config.SERVER_PORT.getValue();
        System.out.println(String.format("%s:%s", serverIp, serverPort));
    }
}
