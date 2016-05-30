package com.xdc.basic.api.apache.commons.configuration;

import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * PropertiesConfiguration 这个类在内存中有缓存，设置文件改变策略后，当文件变化时会重新读取属性文件刷新内存
 * 
 * 配置文件项的值支持使用\转义，规则同java 中文字符使用Unicode编码，如中文“中国”为\u4e2d\u56fd
 * 
 * @author xdc
 * 
 */
public class PropertiesConfigurationTest
{
    public static void main(String[] args) throws ConfigurationException, InterruptedException
    {
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
        config.addConfigurationListener(new ConfigurationLogListener());
        config.setEncoding("UTF-8");
        config.setFileName("src/common-logging.properties");

        // 读取属性
        String log = config.getString("org.apache.commons.logging.Log");
        System.out.println("当前使用的日志: " + log);

        // 设置内存属性
        config.setProperty("org.apache.commons.logging.Log", "otherLog");

        // 写入文件
        config.save();

        // 下面不停地打印配置，验证设置的更改策略
        while (true)
        {
            String curLog = config.getString("org.apache.commons.logging.Log");
            System.out.println("当前使用的日志: " + curLog + " 时间: " + new Date());

            Thread.sleep(1000L);
        }
    }

    // 遗留问题：研究正确的使用方法，避免下面的问题。
    // 1. 使用不当，可能会造成注释丢失
    // 2. 使用不当，可能会造成出现重复记录
    //
    // config.clear();
    // config.load();
    // config.refresh();
    // config.reload();
    // config.reload(true);
}
