package com.xdc.basic.api;

import java.text.MessageFormat;
import java.util.Date;

/**
 * http://www.oschina.net/code/snippet_109139_5062
 * 
 * @author xdc
 * 
 */
public class MessageFormatTest
{
    public static void main(String[] args)
    {
        // 1
        String model = "Hello {0}:  Your name:{0} Age:{1} Address:{2}";
        String[] params = { "Asa", "23", "QiLiHe lz.China" };
        String msg1 = MessageFormat.format(model, (Object[]) params);
        System.out.println(msg1);
        // Hello Asa: Your name:Asa Age:23 Address:QiLiHe lz.China

        // 2
        Object[] params2 = new Object[] { new Date(), new Date(0), new Date() };
        String msg2 = MessageFormat.format("{0,date,short} --- {1,time,medium} --- {2,date,yyyy-MM-dd HH:mm:ss}",
                params2);
        System.out.println(msg2);
        // 11-7-6 --- 8:00:00 --- 2011-07-06 15:40:59

        // 3
        Object[] params3 = new Object[] { new Double(0.45), new Double(1234.56) };
        String msg3 = MessageFormat.format("{0,number,percent}  --- {1,number,percent}", params3);
        System.out.println(msg3);
        // 45% --- 123,456%

        // 4
        Object[] params4 = new Object[] { new Double(123.45), new Double(1234.56) };
        String msg4 = MessageFormat.format("{0,number,currency} --- {1,number,currency}", params4);
        System.out.println(msg4);
        // ￥123.45 --- ￥1,234.56

        // 5
        Object[] params5 = new Object[] { new Integer(123), new Integer(1234) };
        String msg5 = MessageFormat.format("{0,number,#} a''s and {1,number,#} b''s", params5);
        System.out.println(msg5);
        // 123 a's and 1234 b's
    }
}
