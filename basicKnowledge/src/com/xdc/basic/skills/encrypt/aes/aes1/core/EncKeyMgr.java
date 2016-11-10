package com.xdc.basic.skills.encrypt.aes.aes1.core;

import org.junit.Test;

import com.xdc.basic.skills.encrypt.aes.aes1.util.CRC16Util;
import com.xdc.basic.skills.encrypt.aes.aes1.util.CodecUtil;
import com.xdc.basic.skills.encrypt.aes.aes1.util.RandomUtil;
import com.xdc.basic.skills.encrypt.aes.aes1.util.SystemUtil;

public class EncKeyMgr
{
    /**
     * 生成秘钥因子
     */
    public static String genRootKeyHex()
    {
        byte[] randomBytes = RandomUtil.randomBytes(16);
        return CodecUtil.bytes2HexString(randomBytes);
    }

    /**
     * 生成工作秘钥
     */
    public static String genWorkKeyHex()
    {
        byte[] randomBytes = RandomUtil.randomBytes(16);
        return CodecUtil.bytes2HexString(randomBytes);
    }

    /**
     * 根据秘钥因子，加密工作秘钥
     */
    public static String encodeWorkKeyHexWithRootKeyHex(String plainWorkKeyHex, String rootKeyHex, String algorithm,
            int keyLength, int iterationCount) throws EncException
    {
        byte[] initKeyBytes = Enc.encodeWithPBKDF2(rootKeyHex.toCharArray(), EncKeyHolder.initVector, algorithm,
                keyLength, iterationCount);
        String initKeyHex = CodecUtil.bytes2HexString(initKeyBytes);
        return Enc.encode(plainWorkKeyHex, initKeyHex);
    }

    /**
     * 根据秘钥因子，解密工作秘钥
     */
    public static String decodeWorkKeyHexWithRootKeyHex(String cipherWorkKeyHex, String rootKeyHex, String algorithm,
            int keyLength, int iterationCount) throws EncException
    {
        byte[] initKeyBytes = Enc.encodeWithPBKDF2(rootKeyHex.toCharArray(), EncKeyHolder.initVector, algorithm,
                keyLength, iterationCount);
        String initKeyHex = CodecUtil.bytes2HexString(initKeyBytes);
        return Enc.decode(cipherWorkKeyHex, initKeyHex);
    }

    /**
     * 根据目录中的秘钥因子，加密工作秘钥
     */
    public static String encodeWorkKeyHex(String plainWorkKeyHex, String keyPath) throws EncException
    {
        String initKeyHex = EncKeyHolder.getInitKeyHex(keyPath);
        return Enc.encode(plainWorkKeyHex, initKeyHex);
    }

    /**
     * 根据目录中的秘钥因子，解密工作秘钥
     */
    public static String decodeWorkKeyHex(String cipherWorkKeyHex, String keyPath) throws EncException
    {
        String initKeyHex = EncKeyHolder.getInitKeyHex(keyPath);
        return Enc.decode(cipherWorkKeyHex, initKeyHex);
    }

    /**
     * 替换秘钥因子中，根据oldKeyPath的秘钥因子、工作秘钥密文和newKeyPath中的密钥因子，将oldKeyPath的工作秘钥先解密再加密获得新工作秘钥密文
     */
    public static String updateWorkKeyCipherTextInChangingKeyFactor(String oldKeyPath, String newKeyPath)
            throws EncException
    {
        // 工作秘钥明文
        String workKeyHex = EncKeyHolder.getWorkKeyHex(oldKeyPath);

        // 新初始秘钥
        String newInitKeyHex = EncKeyHolder.getInitKeyHex(newKeyPath);

        // 新工作秘钥密文
        String newWorkKeyCipherText = Enc.encode(workKeyHex, newInitKeyHex);
        return newWorkKeyCipherText;
    }

    /**
     * 替换工作秘钥中，根据oldKeyPath和newKeyPath，将系统中已存在的密文先加密再解密
     */
    public static String updatePasswordInChangingWorkKey(String oldCipherText, String oldKeyPath, String newKeyPath)
            throws EncException
    {
        String oldWorkKeyHex = EncKeyHolder.getWorkKeyHex(oldKeyPath);
        String newWorkKeyHex = EncKeyHolder.getWorkKeyHex(newKeyPath);

        String plainText = Enc.decode(oldCipherText, oldWorkKeyHex);
        String newCipherText = Enc.encode(plainText, newWorkKeyHex);
        return newCipherText;
    }

    /**
     * 生成秘钥信息，在替换秘钥的时候，手工将其写入到配置文件即可
     */
    @Test
    public void genKey() throws EncException
    {
        String algorithm = "PBKDF2WithHmacSHA1";
        int keyLength = 128;
        int iterationCount = 100000;

        String workKeyHex = genWorkKeyHex();
        String rootKeyHex = genRootKeyHex();

        String rootKeyCrc = CRC16Util.crc(CodecUtil.hexString2Bytes(rootKeyHex));

        SystemUtil.outPrintln("workKeyHex: " + workKeyHex);
        SystemUtil.outPrintln("rootKeyHex: " + rootKeyHex);
        SystemUtil.outPrintln("rootKeyCrc: " + rootKeyCrc);

        String cipherWorkKeyHex = encodeWorkKeyHexWithRootKeyHex(workKeyHex, rootKeyHex, algorithm, keyLength,
                iterationCount);
        String plainWorkKeyHex = decodeWorkKeyHexWithRootKeyHex(cipherWorkKeyHex, rootKeyHex, algorithm, keyLength,
                iterationCount);

        SystemUtil.outPrintln("cipherWorkKeyHex: " + cipherWorkKeyHex);
        SystemUtil.outPrintln("plainWorkKeyHex: " + plainWorkKeyHex);
    }

    /**
     * 替换秘钥流程，需要shell配合(仅写出流程，不具体实现)
     */
    @Test
    public void changeKey() throws EncException
    {
        // 替换秘钥因子流程
        // 1. 原始目录为KeyDir
        // 2. 复制目录KeyDir为OldKeyDir (shell)
        // 3. 复制目录KeyDir为NewKeyDir (shell)
        // 4. 随机生成秘钥因子和相应的CRC (java)
        // 5. 将密钥因子和相应的CRC替换到NewKeyDir (java或shell)
        // 6. 根据OldKeyDir的秘钥因子、工作秘钥密文和NewKeyDir中的密钥因子，将OldKeyDir的工作秘钥先解密再加密获得新工作秘钥密文 (java)
        // 7. 将工作秘钥替换到NewKeyDir (java或shell)
        // 8. 将NewKeyDir中的文件替换到KeyDir (shell)
        // 9. 删除OldKeyDir和NewKeyDir (shell)
        //
        // 替换工作秘钥流程(其中4和5包括替换秘钥因子，可选)
        // 1. 原始目录为KeyDir
        // 2. 复制目录KeyDir为OldKeyDir (shell)
        // 3. 复制目录KeyDir为NewKeyDir (shell)
        // 4. 随机生成秘钥因子和相应的CRC (java)
        // 5. 将密钥因子和相应的CRC替换到NewKeyDir (java或shell)
        // 6. 随机生成工作秘钥 (java)
        // 7. 根据NewKeyDir中的密钥因子，加密工作秘钥获得工作秘钥密文 (java)
        // 8. 将工作秘钥密文替换到NewKeyDir (java或shell)
        // 9. 根据OldKeyDir和NewKeyDir，将系统中已存在的密文先加密再解密 (java)
        // 10. 将新密文写回系统 (java或shell)
        // 11. 将NewKeyDir中的文件替换到KeyDir (shell)
        // 12. 删除OldKeyDir和NewKeyDir (shell)
    }
}
