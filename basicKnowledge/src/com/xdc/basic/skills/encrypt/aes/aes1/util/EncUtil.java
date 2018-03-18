package com.xdc.basic.skills.encrypt.aes.aes1.util;

import com.xdc.basic.commons.PathUtil;
import com.xdc.basic.skills.encrypt.aes.aes1.core.Enc;
import com.xdc.basic.skills.encrypt.aes.aes1.core.EncException;
import com.xdc.basic.skills.encrypt.aes.aes1.core.EncKeyHolder;

public class EncUtil
{
    public static String encode(String plainText) throws EncException
    {
        String curPath = PathUtil.getRelativePath();
        String keyPath = PathUtil.connect(curPath, "..", "core");

        String workKeyHex = EncKeyHolder.getWorkKeyHex(keyPath);
        String cipherText = Enc.encode(plainText, workKeyHex);
        return cipherText;
    }

    public static String decode(String cipherText) throws EncException
    {
        String curPath = PathUtil.getRelativePath();
        String keyPath = PathUtil.connect(curPath, "..", "core");

        String workKeyHex = EncKeyHolder.getWorkKeyHex(keyPath);
        String plainText = Enc.decode(cipherText, workKeyHex);
        return plainText;
    }
}
