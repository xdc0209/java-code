package com.xdc.basic.skills;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class RMBUtils
{
    public final static String Prefix    = "人民币";
    public final static String Postfix   = "整";
    public final static String Digits    = "零壹贰叁肆伍陆柒捌玖";
    public final static String Positions = "分角元拾佰仟万拾佰仟亿拾佰仟";

    public static String convert(double money) throws Exception
    {
        String rmb = double2String(money);

        int pointIndex = getPointIndex(rmb);
        int positionCursor = pointIndex + 1;
        if (positionCursor >= Positions.length())
        {
            throw new Exception("钱数超出范围！");
        }

        StringBuilder resultSB = new StringBuilder();

        // 不做特殊判断，见阿拉伯数字转成中文
        digits2RMB(rmb, positionCursor, resultSB);

        // 去除零后面的单位，但不包括亿、万、元
        wipeUnitAfter0(resultSB);

        // 去除重复零，变为一个
        wipeRepeat0(resultSB);

        // 去除亿、万、元前面的零，并清除末尾0
        wipe0BeforeYiWanYuanAndLast(resultSB);

        // 添加后缀
        postfix(resultSB);

        // 添加前缀
        return Prefix + resultSB.toString();
    }

    /**
     * @param resultSB
     */
    private static void postfix(StringBuilder resultSB)
    {
        if (resultSB.indexOf("元") == resultSB.length() - 1)
        {
            resultSB.append(Postfix);
        }
        else
        {
            int index0Yuan = resultSB.indexOf("零元");
            if (index0Yuan != -1)
            {
                // 这个用例：assertEquals("人民币壹分", RMBUtils.convert(0.01D));
                if (resultSB.charAt(index0Yuan + 2) == '零')
                {
                    resultSB.delete(index0Yuan, index0Yuan + 3);
                }
                else
                {
                    resultSB.delete(index0Yuan, index0Yuan + 2);
                }
            }
        }
    }

    /**
     * @param resultSB
     */
    private static void wipe0BeforeYiWanYuanAndLast(StringBuilder resultSB)
    {
        for (int i = 0; i < resultSB.length(); i++)
        {
            if (resultSB.charAt(i) == '亿')
            {
                if (resultSB.charAt(i - 1) == '零')
                {
                    resultSB.deleteCharAt(i - 1);
                }
                continue;
            }
            if (resultSB.charAt(i) == '万')
            {
                if (resultSB.charAt(i - 1) == '零')
                {
                    resultSB.deleteCharAt(i - 1);
                }
                continue;
            }
            if (resultSB.charAt(i) == '元')
            {
                // 以零元开头不去除零
                if (resultSB.charAt(i - 1) == '零' && i - 1 != 0)
                {
                    resultSB.deleteCharAt(i - 1);
                }
                continue;
            }
        }

        // 这个用例：assertEquals("人民币壹仟亿零贰万元整", RMBUtils.convert(100000020000D));
        int indexWanYi = resultSB.indexOf("亿万");
        if (indexWanYi != -1)
        {
            resultSB.deleteCharAt(indexWanYi + 1);
        }

        // 这个用例： assertEquals("人民币壹元整", RMBUtils.convert(1D));
        int last = resultSB.length() - 1;
        if (resultSB.charAt(last) == '零')
        {
            resultSB.deleteCharAt(last);
        }
    }

    /**
     * @param resultSB
     */
    private static void wipeRepeat0(StringBuilder resultSB)
    {
        for (int i = 0; i < resultSB.length(); i++)
        {
            if (resultSB.charAt(i) == '零')
            {
                while (i + 1 < resultSB.length() && resultSB.charAt(i + 1) == '零')
                {
                    resultSB.deleteCharAt(i + 1);
                }
            }
        }
    }

    /**
     * @param resultSB
     */
    private static void wipeUnitAfter0(StringBuilder resultSB)
    {
        for (int i = 0; i < resultSB.length(); i++)
        {
            if (resultSB.charAt(i) == '零')
            {
                String position = String.valueOf(resultSB.charAt(i + 1));
                if (!"元万亿".contains(position))
                {
                    resultSB.deleteCharAt(i + 1);
                }
            }
        }
    }

    /**
     * @param rmb
     * @param positionCursor
     * @return
     */
    private static void digits2RMB(String rmb, int positionCursor, StringBuilder resultSB)
    {
        for (int i = 0; i < rmb.length(); i++)
        {
            char c = rmb.charAt(i);
            if (c == '.')
            {
                continue;
            }
            int digit = c - '0';
            resultSB.append(Digits.charAt(digit));
            resultSB.append(Positions.charAt(positionCursor));
            positionCursor--;
        }
    }

    private static int getPointIndex(String rmb)
    {
        return rmb.indexOf(".");
    }

    /**
     * @param money
     * @return
     */
    private static String double2String(double money)
    {
        BigDecimal bd = new BigDecimal(money);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return bd.toString();
    }

    @Test
    public void testConvert() throws Exception
    {
        assertEquals("人民币壹仟亿零贰万元整", RMBUtils.convert(100000020000D));
        assertEquals("人民币壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整", RMBUtils.convert(123456789012D));
        assertEquals("人民币玖仟玖佰玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元整", RMBUtils.convert(999999999999D));
        assertEquals("人民币伍拾陆万柒仟捌佰玖拾元整", RMBUtils.convert(567890D));
        assertEquals("人民币壹佰元整", RMBUtils.convert(100D));
        assertEquals("人民币壹元整", RMBUtils.convert(1D));
        assertEquals("人民币零元整", RMBUtils.convert(0D));

        assertEquals("人民币壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元零壹分", RMBUtils.convert(1234567890.01D));
        assertEquals("人民币玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元壹角壹分", RMBUtils.convert(9999999999.11D));
        assertEquals("人民币伍拾陆万柒仟捌佰玖拾元玖角贰分", RMBUtils.convert(567890.92D));
        assertEquals("人民币壹佰元叁角玖分", RMBUtils.convert(100.39D));
        assertEquals("人民币壹元贰角贰分", RMBUtils.convert(1.22D));
        assertEquals("人民币玖角玖分", RMBUtils.convert(0.99D));
        assertEquals("人民币壹分", RMBUtils.convert(0.01D));
    }
}
