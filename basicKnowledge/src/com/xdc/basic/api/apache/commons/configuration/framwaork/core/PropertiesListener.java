package com.xdc.basic.api.apache.commons.configuration.framwaork.core;

public abstract class PropertiesListener
{
    /**
     * 如果对此事件的处理事件较长，建议在此方法内启动新线程处理。
     */
    public abstract void configurationChanged(String propertiesFilePath, String key, String oldValue, String newValue);
}
