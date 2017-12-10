package com.xdc.basic.api.apache.commons.configuration.framwaork.config;

import com.xdc.basic.api.apache.commons.configuration.framwaork.core.PropertiesHolder;
import com.xdc.basic.api.apache.commons.configuration.framwaork.core.Property;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.IntegerValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.IpValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.PortValidator;

// 注意：接口的属性的权限默认是public static final。
public interface Module2Config
{
    PropertiesHolder  p           = new PropertiesHolder(PathConfig.getModule2ConfigPath());

    Property<Integer> KEY1        = new Property<Integer>(p, "key1", "200", new IntegerValidator(), Integer.class);

    Property<String>  KEY2        = new Property<String>(p, "key2", null, null, String.class);

    Property<String>  SERVER_IP   = new Property<String>(p, "server.ip", "127.0.0.1", new IpValidator(), String.class);

    Property<String>  SERVER_PORT = new Property<String>(p, "server.port", "8080", new PortValidator(), String.class);
}
