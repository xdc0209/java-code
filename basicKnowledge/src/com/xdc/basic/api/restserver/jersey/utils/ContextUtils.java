package com.xdc.basic.api.restserver.jersey.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

/**
 * JAX-RS客户端、服务端请求上下文处理工具。
 */
public class ContextUtils
{
    /**
     * 将JAX-RS客户端、服务端请求上下文中的多值映射表转换为普通的单一值映射表。
     * 
     * @param params
     *            待转换的多值映射表
     * @return 转换后的单一值映射表
     */
    public static Map<String, String> transform(MultivaluedMap<String, String> params)
    {
        Map<String, String> result = new LinkedHashMap<String, String>();

        for (Entry<String, List<String>> entry : params.entrySet())
        {
            List<String> values = entry.getValue();
            if (values.size() > 1)
            {
                StringBuilder sb = new StringBuilder(256);
                Iterator<String> it = values.iterator();
                while (it.hasNext())
                {
                    sb.append(it.next());
                    if (it.hasNext())
                    {
                        sb.append(',');
                    }
                }
                result.put(entry.getKey(), sb.toString());
            }
            else
            {
                result.put(entry.getKey(), values.get(0));
            }
        }

        return result;
    }
}
