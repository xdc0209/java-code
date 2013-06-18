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

        StringBuilder resultSB = digits2RMB(rmb, positionCursor);

        int index0Jiao = resultSB.indexOf("零角");
        if (index0Jiao != -1)
        {
            resultSB.delete(index0Jiao, index0Jiao + 2);
        }

        int index0Fen = resultSB.indexOf("零分");
        if (index0Fen != -1)
        {
            resultSB.delete(index0Fen, index0Fen + 2);
        }

        wipeUnitAfter0(resultSB);

        wipeRepeat0(resultSB);

        wipe0BeforeYiWanYuan(resultSB);

        if (resultSB.indexOf("元") == resultSB.length() - 1)
        {
            resultSB.append(Postfix);
        }
        else
        {
            int index0Yuan = resultSB.indexOf("零元");
            if (index0Yuan != -1)
            {
                resultSB.delete(index0Yuan, index0Yuan + 2);
            }
        }

        return Prefix + resultSB.toString();
    }

    /**
     * @param resultSB
     */
    private static void wipe0BeforeYiWanYuan(StringBuilder resultSB)
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
                if (resultSB.charAt(i - 1) == '零' && i - 1 != 0)
                {
                    resultSB.deleteCharAt(i - 1);
                }
                continue;
            }
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
                while (resultSB.charAt(i + 1) == '零')
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
    private static StringBuilder digits2RMB(String rmb, int positionCursor)
    {
        StringBuilder resultSB = new StringBuilder();
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
        return resultSB;
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
        assertEquals("人民币壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整", RMBUtils.convert(123456789012D));
        assertEquals("人民币玖仟玖佰玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元整", RMBUtils.convert(999999999999D));
        assertEquals("人民币伍拾陆万柒仟捌佰玖拾元整", RMBUtils.convert(567890D));
        assertEquals("人民币壹佰元整", RMBUtils.convert(100D));
        assertEquals("人民币壹元整", RMBUtils.convert(1D));
        assertEquals("人民币零元整", RMBUtils.convert(0D));

        assertEquals("人民币壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元壹分", RMBUtils.convert(1234567890.01D));
        assertEquals("人民币玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元壹角壹分", RMBUtils.convert(9999999999.11D));
        assertEquals("人民币伍拾陆万柒仟捌佰玖拾元玖角贰分", RMBUtils.convert(567890.92D));
        assertEquals("人民币壹佰元叁角玖分", RMBUtils.convert(100.39D));
        assertEquals("人民币壹元贰角贰分", RMBUtils.convert(1.22D));
        assertEquals("人民币玖角玖分", RMBUtils.convert(0.99D));
        assertEquals("人民币壹分", RMBUtils.convert(0.01D));
    }
}
