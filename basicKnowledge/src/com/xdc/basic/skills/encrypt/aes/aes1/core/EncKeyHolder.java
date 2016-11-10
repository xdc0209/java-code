package com.xdc.basic.skills.encrypt.aes.aes1.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;

import com.xdc.basic.skills.GetPath;
import com.xdc.basic.skills.encrypt.aes.aes1.util.CRC16Util;
import com.xdc.basic.skills.encrypt.aes.aes1.util.CodecUtil;

public class EncKeyHolder
{
    /**
     * 缓存不同路径下的工作秘钥，避免每次都访问文件（Key:路径，value：工作秘钥）
     */
    private static Map<String, String> workKeyHexMap                    = new ConcurrentHashMap<String, String>();

    private static final String        ENC_ROOT_KEY_CONF                = "enc.root.key.conf";
    private static final String        ENC_ROOT_KEY                     = "enc.root.key";
    private static final String        ENC_ROOT_KEY_CRC                 = "enc.root.key.crc";
    private static final String        ENC_ROOT_KEY_GEN_ALGORITHM       = "enc.root.key.gen.algorithm";
    private static final String        ENC_ROOT_KEY_GEN_LENGTH          = "enc.root.key.gen.length";
    private static final String        ENC_ROOT_KEY_GEN_ITERATION_COUNT = "enc.root.key.gen.iteration.count";

    private static final String        ENC_WORK_KEY_CONF                = "enc.work.key.conf";
    private static final String        ENC_WORK_KEY                     = "enc.work.key";

    // 初始向量的赋值：可以编写个临时小工具，生成如下赋值代码。（代码规则：先生成随机数，再进行随机运算）
    // 这样做的好处：
    // 1. 攻击者无法通过字符串搜索的方式查找出初始向量数组。
    // 2. 避免出现连续赋值，以随机运算替换，提升了逆向攻击难度。
    public static final byte[]         initVector                       = { (byte) 0x73, (byte) 0x5C, (byte) 0x55,
            (byte) 0x9E, (byte) 0x60, (byte) 0x35, (byte) 0xF7, (byte) 0x1F, (byte) 0x96, (byte) 0xF9, (byte) 0x49,
            (byte) 0x6E, (byte) 0x6F, (byte) 0x29, (byte) 0x18, (byte) 0xC7 };

    static
    {
        initVector[0] = (byte) (initVector[0] * initVector[5]);
        initVector[1] = (byte) (initVector[12] << initVector[5]);
        initVector[2] = (byte) (initVector[5] & initVector[10]);
        initVector[3] = (byte) (initVector[2] << initVector[13]);
        initVector[4] = (byte) (initVector[13] + initVector[0]);
        initVector[5] = (byte) (initVector[0] | initVector[11]);
        initVector[6] = (byte) (initVector[2] * initVector[13]);
        initVector[7] = (byte) (initVector[15] | initVector[2]);
        initVector[8] = (byte) (initVector[12] * initVector[1]);
        initVector[9] = (byte) (initVector[8] & initVector[1]);
        initVector[10] = (byte) (initVector[10] ^ initVector[11]);
        initVector[11] = (byte) (initVector[8] * initVector[4]);
        initVector[12] = (byte) (initVector[7] + initVector[9]);
        initVector[13] = (byte) (initVector[15] + initVector[0]);
        initVector[14] = (byte) (initVector[11] * initVector[5]);
        initVector[15] = (byte) (initVector[0] & initVector[12]);
    }

    /**
     * 获得初始秘钥（用于加密工作秘钥）
     */
    public static String getInitKeyHex(String keyPath) throws EncException
    {
        try
        {
            Properties p = loadProperties(GetPath.connect(keyPath, ENC_ROOT_KEY_CONF));

            String rootKey = p.getProperty(ENC_ROOT_KEY);
            String rootKeyCRC = p.getProperty(ENC_ROOT_KEY_CRC);
            if (!CRC16Util.match(CodecUtil.hexString2Bytes(rootKey), rootKeyCRC))
            {
                throw new EncException("root key and root key crc not match.");
            }

            String algorithm = p.getProperty(ENC_ROOT_KEY_GEN_ALGORITHM, "PBKDF2WithHmacSHA1");
            int keyLength = Integer.valueOf(p.getProperty(ENC_ROOT_KEY_GEN_LENGTH, "128"));
            int iterationCount = Integer.valueOf(p.getProperty(ENC_ROOT_KEY_GEN_ITERATION_COUNT, "100000"));

            byte[] initKeyBytes = Enc.encodeWithPBKDF2(rootKey.toCharArray(), EncKeyHolder.initVector, algorithm,
                    keyLength, iterationCount);

            return CodecUtil.bytes2HexString(initKeyBytes);
        }
        catch (Exception e)
        {
            throw new EncException("Get init key hex failed.", e);
        }
    }

    /**
     * 获得工作秘钥（用于加密密码等敏感数据）
     */
    public static String getWorkKeyHex(String keyPath) throws EncException
    {
        String workKeyHex = workKeyHexMap.get(keyPath);
        if (workKeyHex == null)
        {
            Properties p = loadProperties(GetPath.connect(keyPath, ENC_WORK_KEY_CONF));

            String workKey = p.getProperty(ENC_WORK_KEY);

            workKeyHex = Enc.decode(workKey, getInitKeyHex(keyPath));
            workKeyHexMap.put(keyPath, workKeyHex);
        }

        return workKeyHex;
    }

    /**
     * 读取配置文件
     */
    private static Properties loadProperties(String fileName) throws EncException
    {
        Properties p = null;
        InputStream in = null;

        try
        {
            in = new FileInputStream(fileName);
            p = new Properties();
            p.load(in);
        }
        catch (IOException e)
        {
            throw new EncException("Read config failed.", e);
        }
        finally
        {
            IOUtils.closeQuietly(in);
        }

        return p;
    }
}
