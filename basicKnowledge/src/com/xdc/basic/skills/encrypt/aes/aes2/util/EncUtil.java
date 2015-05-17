package com.xdc.basic.skills.encrypt.aes.aes2.util;

import com.xdc.basic.skills.GetPath;
import com.xdc.basic.skills.encrypt.aes.aes2.core.Enc;
import com.xdc.basic.skills.encrypt.aes.aes2.core.EncException;
import com.xdc.basic.skills.encrypt.aes.aes2.core.EncKeyHolder;

public class EncUtil
{
    public static String encode(String plainText) throws EncException
    {
        String curPath = GetPath.getRelativePath();
        String keyPath = GetPath.connect(curPath, "..", "core");

        byte[] workKeyBytes = EncKeyHolder.getWorkKeyBytes(keyPath);
        String cipherText = Enc.encode(plainText, workKeyBytes);
        return cipherText;
    }

    public static String decode(String cipherText) throws EncException
    {
        String curPath = GetPath.getRelativePath();
        String keyPath = GetPath.connect(curPath, "..", "core");

        byte[] workKeyBytes = EncKeyHolder.getWorkKeyBytes(keyPath);
        String plainText = Enc.decode(cipherText, workKeyBytes);
        return plainText;
    }
}
