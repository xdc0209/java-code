package com.xdc.basic.commons;

import java.nio.ByteBuffer;

public class ByteBufferUtil
{
    /**
     * 注意：编码规范要求：一定要返回一个只读的ByteBuffer
     */
    public static ByteBuffer bytes2ByteBuffer(byte[] bytes)
    {
        if (bytes == null)
        {
            return null;
        }

        return ByteBuffer.wrap(bytes).asReadOnlyBuffer();
    }

    /**
     * 深度拷贝，不对原始对象有任何更改。
     * 注意：编码规范要求：不能简单实用byteBuffer.array()
     */
    public static byte[] byteBuffer2Bytes(ByteBuffer byteBuffer)
    {
        if (byteBuffer == null)
        {
            return null;
        }

        byteBuffer.rewind();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        byteBuffer.rewind();
        return bytes;
    }
}
