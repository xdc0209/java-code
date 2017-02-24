package com.xdc.basic.api.apache.commons.configuration.framwaork.core;

import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.Validator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate1.validators.atomic.LongValidator;
import com.xdc.basic.api.thread.executor.threadfactory.NamePrefixThreadFactory;

public class PropertiesHolder
{
    /**
     * 日志工具类
     */
    private static Logger                   logger                   = LoggerFactory.getLogger(PropertiesHolder.class);

    /**
     * properties文件路径
     */
    private String                          propertiesFilePath       = null;

    /**
     * 内部使用Apache的属性文件操作类实现
     */
    private PropertiesConfiguration         propertiesConfiguration  = null;

    /**
     * 检查配置文件变更线程
     */
    private static ScheduledExecutorService scheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor(new NamePrefixThreadFactory("Check-Config-File-Change"));

    public PropertiesHolder(String propertiesFilePath)
    {
        try
        {
            if (!FileUtils.getFile(propertiesFilePath).exists())
            {
                throw new FileNotFoundException(propertiesFilePath);
            }

            this.propertiesFilePath = propertiesFilePath;

            // PropertiesConfiguration的使用要点，一定要看，否则容易掉坑。
            // PropertiesConfiguration.clear(); // 清除内存中的配置项集合。
            // PropertiesConfiguration.load(); // 读取磁盘中的配置项集合到内存。千万注意：调用PropertiesConfiguration.setReloadingStrategy()后，禁止调用此方法，因为此时用此方法会导致内存中的配置项的值重复。
            // PropertiesConfiguration.refresh(); // 相当于先clear()，再load()。
            // PropertiesConfiguration.reload(); // 先检测文件时间戳是否变更，如果有变化，再调用refresh()。
            // PropertiesConfiguration.save(); // 写入文件，写入时可以保证头部注释、配置项注释、空行数量都写入正确，但尾部注释会被丢弃，不过不是问题，没有人会用尾部注释。设置autoSave为true后，不必单独调用此方法。
            // PropertiesConfiguration.clearProperty(String key) // 删除指定key的配置项
            // PropertiesConfiguration.setProperty(String key, Object value); // 设置指定key的配置项，不存在则添加，存在则覆盖。推荐使用。
            // PropertiesConfiguration.addProperty(String key, Object value); // 添加指定key的配置项，不存在则添加，存在则追加为数组。一般不使用此方法。
            //
            // 通过以上分析得出结论，初始化配置的方法有两种：
            // （1）先调用PropertiesConfiguration.load()，再调用PropertiesConfiguration.setReloadingStrategy()；
            // （2）先调用PropertiesConfiguration.setReloadingStrategy()，再调用PropertiesConfiguration.reload()。
            propertiesConfiguration = new PropertiesConfiguration();

            // 禁用抛异常：配置项的key不存在时返回null，而不是报异常
            propertiesConfiguration.setThrowExceptionOnMissing(false);

            // 禁用解析成数组：当配置项的value存在分隔符时，只取原样字符串，而不是字符串数组
            propertiesConfiguration.setDelimiterParsingDisabled(true);

            // 当调用PropertiesConfiguration.setProperty等更改操作时，自动写入磁盘
            propertiesConfiguration.setAutoSave(true);

            // 设置编码为UTF-8
            propertiesConfiguration.setEncoding("UTF-8");

            // 设置properties文件路径
            propertiesConfiguration.setFileName(propertiesFilePath);

            // 初始化读取配置
            propertiesConfiguration.load();

            // 获取配置文件的刷新时间
            long refreshDelay = Long.valueOf(
                    getString("properties.refresh.delay", null, "60000", new LongValidator(5000L, Long.MAX_VALUE)));

            // 设置热生效时间为60s
            FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
            fileChangedReloadingStrategy.setRefreshDelay(refreshDelay);
            propertiesConfiguration.setReloadingStrategy(fileChangedReloadingStrategy);
            scheduledExecutorService.scheduleAtFixedRate(new Runnable()
            {
                @Override
                public void run()
                {
                    // Apache的配置变更实现时，没有启动线程定期检查是否变更，而是每次读取配置文件时，先检查下次检查时间是否到了，如果到了，再读取文件时间戳，看看跟上次检查是否有变化，如果有变化，在重新加载文件。
                    // 为了保证本框架的变更通知机制不依赖与配置文件的读取，定期调用PropertiesConfiguration.isEmpty()，isEmpty中会检查文件变更，并重新加载配置。
                    propertiesConfiguration.isEmpty();
                }
            }, refreshDelay, refreshDelay, TimeUnit.MILLISECONDS);
        }
        catch (Throwable e)
        {
            logger.error(
                    String.format("Construct PropertiesHolder failed, propertiesFilePath=[%s].", propertiesFilePath),
                    e);

            throw new RuntimeException(e);
        }
    }

    public String getString(String key, String oldValue, String defaultValue, Validator validator)
    {
        String newValue = propertiesConfiguration.getString(key, null);

        // 如果新值不为空，且新值等于旧值，直接返回新值(或旧值)。因为旧值已经经过了校验，为了提高效率，不用再校验了。
        if (StringUtils.isNotEmpty(newValue) && StringUtils.equals(newValue, oldValue))
        {
            logger.debug(
                    "Get string success, newValue and oldValue is same, return newValue. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}].",
                    FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue);
            return newValue;
        }

        // 新值、旧值、默认值都为空，直接返回空
        if (StringUtils.isEmpty(newValue) && StringUtils.isEmpty(oldValue) && StringUtils.isEmpty(defaultValue))
        {
            logger.debug(
                    "Get string success, newValue, oldValue and defaultValue is empty, return newValue. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}], defaultValue=[{}].",
                    FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue, defaultValue);
            return newValue;
        }

        // 如果新值合法直接返回新值
        if (validator.validate(newValue))
        {
            logger.debug(
                    "Get string success, newValue is valid, return newValue. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}].",
                    FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue);
            return newValue;
        }
        logger.debug(
                "NewValue is not valid. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}], validator=[{}].",
                FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue, validator.detail());

        // 如果新值非法，旧值合法，返回旧值
        if (validator.validate(oldValue))
        {
            logger.debug(
                    "Get string success, newValue is not valid, oldValue is valid, return oldValue. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}].",
                    FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue);
            return oldValue;
        }
        logger.debug(
                "OldValue is not valid. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}], validator=[{}].",
                FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue, validator.detail());

        logger.debug(
                "Get string success, newValue is not valid, oldValue is not valid, return defaultValue. propertiesFilePath=[{}], key=[{}], newValue=[{}], oldValue=[{}], defaultValue=[{}].",
                FilenameUtils.getName(propertiesFilePath), key, newValue, oldValue, defaultValue);
        // 新值非法，旧值非法，返回默认值
        return defaultValue;
    }

    public void setString(String key, String newValue, Validator validator)
    {
        if (!validator.validate(newValue))
        {
            String error = String.format(
                    "Set string failed, due to validating value failed. propertiesFilePath=[%s], key=[%s], newValue=[%s], validator=[%s].",
                    FilenameUtils.getName(propertiesFilePath), key, newValue, validator.detail());
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

        // 校验配置项的值合法性
        propertiesConfiguration.setProperty(key, newValue);
    }

    public void addListener(final String key, String oldValue, String defaultValue, Validator validator,
            final PropertiesListener propertiesListener)
    {
        propertiesConfiguration.addConfigurationListener(
                new InternalPropertiesListener(key, oldValue, defaultValue, validator, propertiesListener));
    }

    class InternalPropertiesListener implements ConfigurationListener
    {
        private String             key;
        private String             oldValue;
        private String             defaultValue;
        private Validator          validator;
        private PropertiesListener propertiesListener;

        public InternalPropertiesListener(String key, String oldValue, String defaultValue, Validator validator,
                PropertiesListener propertiesListener)
        {
            super();
            this.key = key;
            this.oldValue = oldValue;
            this.defaultValue = defaultValue;
            this.validator = validator;
            this.propertiesListener = propertiesListener;
        }

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
            // Apache的配置变更实现时，每个变更都发送变更前和变更后两个事件，我们只关注变更后的事件
            if (event.isBeforeUpdate())
            {
                return;
            }

            logger.debug("The properties file [{}] is changed. configurationEvent=[{}].",
                    FilenameUtils.getName(propertiesFilePath),
                    ToStringBuilder.reflectionToString(event, ToStringStyle.MULTI_LINE_STYLE));

            String newValue = getString(key, oldValue, defaultValue, validator);
            if (!StringUtils.equals(oldValue, newValue))
            {
                try
                {
                    propertiesListener.configurationChanged(propertiesFilePath, key, oldValue, newValue);
                }
                catch (Throwable e)
                {
                    logger.error(String.format(
                            "Handle properties file [%s] changed event fail. key=[%s], oldValue=[%s], newValue=[%s].",
                            FilenameUtils.getName(propertiesFilePath), key, oldValue, newValue), e);
                }

                oldValue = newValue;
            }
        }
    }
}
