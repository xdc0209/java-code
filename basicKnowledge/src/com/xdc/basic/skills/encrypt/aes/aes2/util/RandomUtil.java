package com.xdc.basic.skills.encrypt.aes.aes2.util;

import java.security.SecureRandom;

public class RandomUtil
{
    public static byte[] randomBytes(int length)
    {
        byte[] random = new byte[length];
        SecureRandom rand = new SecureRandom();
        rand.nextBytes(random);
        return random;
    }
}
