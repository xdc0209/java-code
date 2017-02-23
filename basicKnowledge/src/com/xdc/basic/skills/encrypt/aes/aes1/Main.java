package com.xdc.basic.skills.encrypt.aes.aes1;

import com.xdc.basic.skills.encrypt.aes.aes1.core.EncException;
import com.xdc.basic.skills.encrypt.aes.aes1.util.EncUtil;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            String plainText = "123456789012345";
            System.out.println("plainText: " + plainText);

            String encode = EncUtil.encode(plainText);
            System.out.println("encode: " + encode);

            String decode = EncUtil.decode(encode);
            System.out.println("decode: " + decode);
        }
        catch (EncException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
