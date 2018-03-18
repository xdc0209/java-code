package com.xdc.basic.skills.encrypt.aes.aes2.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class CharsUtil
{
    /**
     * chars转bytes。
     * 不经过String，避免在内存中残留。(这个是有争议的，有待确认)
     */
    public static byte[] chars2Bytes(char[] chars)
    {
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();

        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = cs.encode(cb);

        // Warning：不能直接调用bb.array()，否则末尾会多出一个'\0'，参考自：http://stackoverflow.com/questions/5513144/converting-char-to-byte
        byte[] bytes = new byte[bb.remaining()];
        bb.get(bytes);

        return bytes;
    }

    /**
     * bytes转chars。
     * 不经过String，避免在内存中残留。(这个是有争议的，有待确认)
     */
    public static char[] bytes2Chars(byte[] bytes)
    {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();

        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }
}
