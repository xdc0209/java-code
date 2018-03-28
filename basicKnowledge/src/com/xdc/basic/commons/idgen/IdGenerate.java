/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.xdc.basic.commons.idgen;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类。
 * 
 * @see com.jeesite.common.idgen.IdGenerate
 */
public class IdGenerate
{
    private static SecureRandom random   = new SecureRandom();
    private static IdWorker     idWorker = new IdWorker(-1, -1);

    /**
     * 生成UUID，中间无-分割。
     */
    public static String uuid()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成long。
     */
    public static long randomLong()
    {
        return Math.abs(random.nextLong());
    }

    /**
     * 使用SecureRandom随机生成bytes。
     */
    public static byte[] randomBytes(int length)
    {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return randomBytes;
    }

    /**
     * 说明：获取新唯一编号(18为数值)，来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
     * 格式：64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))。
     */
    public static long nextId()
    {
        return idWorker.nextId();
    }
}
