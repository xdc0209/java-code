package com.xdc.basic.api.hibernate.orm3.spring.framwaork.core;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.xdc.basic.skills.encrypt.aes.aes2.core.EncException;
import com.xdc.basic.skills.encrypt.aes.aes2.util.EncUtil;

public class PasswordConverPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    private PasswordConverPropertyPlaceholderConfigurer()
    {
        super();

        // 禁用读取环境变量，有安全风险，因为程序在运行期间环境变量可能改变。
        // 如果需要不可避免的使用环境变量，则将相应的环境变量添加到java启动参数，最起码在程序运行过程中配置不会随环境变量变化
        super.setSearchSystemEnvironment(false);
        super.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_FALLBACK);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException
    {
        // 解密jdbc.password属性值，并重新设置
        String jdbcPassword = props.getProperty("jdbc.password");
        if (StringUtils.isNotBlank(jdbcPassword) && jdbcPassword.matches("ENC(.*)"))
        {
            try
            {
                String plainJdbcPassword = EncUtil.decode(StringUtils.substringBetween(jdbcPassword, "ENC(", ")"));
                props.setProperty("jdbc.password", plainJdbcPassword);
            }
            catch (EncException e)
            {
                e.printStackTrace();
            }
        }

        super.processProperties(beanFactory, props);
    }
}
