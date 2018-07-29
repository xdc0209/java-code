package com.xdc.basic.tools.enc;

import org.apache.commons.codec.digest.DigestUtils;

import com.xdc.basic.skills.encrypt.aes.aes2.core.Enc;
import com.xdc.basic.skills.encrypt.aes.aes2.core.EncException;

public class EncMain
{
    public static String encode(String plainText, String keyText) throws EncException
    {
        byte[] workKeyBytes = DigestUtils.sha256(keyText);
        String cipherText = Enc.encode(plainText, workKeyBytes);
        return cipherText;
    }

    public static String decode(String cipherText, String keyText) throws EncException
    {
        byte[] workKeyBytes = DigestUtils.sha256(keyText);
        String plainText = Enc.decode(cipherText, workKeyBytes);
        return plainText;
    }

    public static void main(String[] args) throws EncException
    {
        System.out.println(encode("12345678", "面朝大海"));
        System.out.println(decode("022881f7ed265026dbf30e41437f09e246b50ef967a910720ae1c00fdd3c93ea4c", "面朝大海"));
    }
}
