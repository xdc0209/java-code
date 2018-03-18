package com.xdc.basic.api.apache.commons.codec.binary;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.xdc.basic.commons.codec.Base64Util;

/**
 * 为什么要使用Base64？
 * 
 * 在设计这个编码的时候，我想设计人员最主要考虑了3个问题：
 * 1.是否加密？
 * 2.加密算法复杂程度和效率
 * 3.如何处理传输？
 * 
 * 加密是肯定的，但是加密的目的不是让用户发送非常安全的Email。这种加密方式主要就是“防君子不防小人”。即达到一眼望去完全看不出内容即可。
 * 基于这个目的加密算法的复杂程度和效率也就不能太大和太低。和上一个理由类似，MIME协议等用于发送Email的协议解决的是如何收发Email，
 * 而并不是如何安全的收发Email。因此算法的复杂程度要小，效率要高，否则因为发送Email而大量占用资源，路就有点走歪了。
 * 
 * 但是，如果是基于以上两点，那么我们使用最简单的恺撒法即可，为什么Base64看起来要比恺撒法复杂呢？这是因为在Email的传送过程中，由于历史原因，
 * Email只被允许传送ASCII字符，即一个8位字节的低7位。因此，如果您发送了一封带有非ASCII字符（即字节的最高位是1）的Email通过有“历史问题
 * ”的网关时就可能会出现问题。网关可能会把最高位置为0！很明显，问题就这样产生了！因此，为了能够正常的传送Email，这个问题就必须考虑！所以，
 * 单单靠改变字母的位置的恺撒之类的方案也就不行了。关于这一点可以参考RFC2046。
 * 基于以上的一些主要原因产生了Base64编码。
 * 
 * @author xdc
 * 
 */
public class Base64Test
{
    public static final String UTF8 = "UTF-8"; // UTF8和UTF-8一样
    public static final String GBK  = "GBK";   // 迅雷要用这个

    public static void main(String[] args)
    {
        String plainText = "I love you, but couldn't let you know. So, encode this.";
        System.out.println("plainText: " + plainText);

        // 加密
        String encodedStr = Base64Util.encodeStr(plainText, UTF8);
        System.out.println("encodeStr: " + encodedStr);

        // 解密
        String decodedStr = Base64Util.decodeStr(encodedStr, UTF8);
        System.out.println("decodeStr: " + decodedStr);

        System.out.println("plainText==decodeStr? : " + plainText.equals(decodedStr));

        // 简单方式，流程如上
        encodedStr = Base64.encodeBase64String(StringUtils.getBytesUtf8(plainText));
        System.out.println("encodeStr: " + encodedStr);
        decodedStr = StringUtils.newStringUtf8(Base64.decodeBase64(encodedStr));
        System.out.println("decodeStr: " + decodedStr);
        System.out.println("plainText==decodeStr? : " + plainText.equals(decodedStr));

        System.out.println("字符集名称：");
        for (String charsetName : Charset.availableCharsets().keySet())
        {
            System.out.println(charsetName);
        }
    }
}
