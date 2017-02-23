package com.xdc.basic.api.apache.commons.configuration;

import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import com.xdc.basic.skills.GetPath;

/**
 * PropertiesConfiguration 这个类在内存中有缓存，设置文件改变策略后，当文件变化时会重新读取属性文件刷新内存
 * 
 * 配置文件项的值支持使用\转义，规则同java中文字符使用Unicode编码，如中文“中国”为\u4e2d\u56fd
 */
public class PropertiesConfigurationTest
{
    public static void main(String[] args) throws ConfigurationException, InterruptedException
    {
        String curPath = GetPath.getRelativePath();
        String propertiesFilePath = GetPath.connect(curPath, "PropertiesConfigurationTest1.properties");

        // PropertiesConfiguration的使用要点，一定要看，否则容易掉坑。
        // PropertiesConfiguration.clear(); // 清除内存中的配置项集合。
        // PropertiesConfiguration.load(); // 读取磁盘中的配置项集合到内存。千万注意：禁止调用此方法，不知道是否为bug，调用此方法会导致内存中的配置项的值重复。该方法内部被refresh()使用，refresh()中的调用方法不会出现重复问题！！！
        // PropertiesConfiguration.refresh(); // 相当于先clear()，再load()。
        // PropertiesConfiguration.reload(); // 先检测文件时间戳是否变更，如果有变化，再调用refresh()。
        // PropertiesConfiguration.save(); // 写入文件，写入时可以保证头部注释、配置项注释、空行数量都写入正确，但尾部注释会被丢弃，不过不是问题，没有人会用尾部注释。设置autoSave为true后，不必单独调用此方法。
        // PropertiesConfiguration.clearProperty(String key) // 删除指定key的配置项
        // PropertiesConfiguration.setProperty(String key, Object value); // 设置指定key的配置项，不存在则添加，存在则覆盖。推荐使用。
        // PropertiesConfiguration.addProperty(String key, Object value); // 添加指定key的配置项，不存在则添加，存在则追加为数组。一般不使用此方法。
        PropertiesConfiguration config = new PropertiesConfiguration();

        // 禁用抛异常：配置项的key不存在时返回null，而不是报异常
        config.setThrowExceptionOnMissing(false);

        // 禁用解析成数组：当配置项的value存在分隔符时，只取原样字符串，而不是字符串数组
        config.setDelimiterParsingDisabled(true);

        // 当调用PropertiesConfiguration.setProperty等更改操作时，自动写入磁盘
        config.setAutoSave(true);

        // 设置热生效时间为30s
        FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
        fileChangedReloadingStrategy.setRefreshDelay(30000);
        config.setReloadingStrategy(fileChangedReloadingStrategy);

        // 设置编码为UTF-8
        config.setEncoding("UTF-8");

        // 设置文件路径
        config.setFileName(propertiesFilePath);

        // 初始化读取配置，注意此处是PropertiesConfiguration.reload()，千万不使用PropertiesConfiguration.load()
        config.reload();

        // 添加监听器，配置项变化会收到通知
        config.addConfigurationListener(new ConfigurationLogListener());

        // 读取配置项
        String key1Value = config.getString("key1");
        System.out.println("key1=" + key1Value);

        // 设置配置项(设置autoSave为true后，设置配置项时同步写入内存和磁盘)
        config.setProperty("key1", "value1-new");
        config.setProperty("key6", "value6");

        // 下面不停地打印配置，验证设置的更改策略
        while (true)
        {
            key1Value = config.getString("key1");
            System.out.println(new Date() + " key1=" + key1Value);

            Thread.sleep(1000L);
        }
    }
}
