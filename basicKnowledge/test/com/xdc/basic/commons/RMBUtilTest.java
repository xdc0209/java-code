package com.xdc.basic.commons;

import org.junit.Assert;
import org.junit.Test;

public class RMBUtilTest
{
    @Test
    public void testConvert()
    {
        Assert.assertEquals("人民币壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整", RMBUtil.convert(123456789012D));
        Assert.assertEquals("人民币玖仟玖佰玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元整", RMBUtil.convert(999999999999D));
        Assert.assertEquals("人民币壹仟亿零贰佰元整", RMBUtil.convert(100000000200D));
        Assert.assertEquals("人民币伍拾陆万柒仟捌佰玖拾元整", RMBUtil.convert(567890D));
        Assert.assertEquals("人民币壹佰元整", RMBUtil.convert(100D));
        Assert.assertEquals("人民币壹元整", RMBUtil.convert(1D));
        Assert.assertEquals("人民币零元整", RMBUtil.convert(0D));

        Assert.assertEquals("人民币壹拾贰亿叁仟肆佰伍拾陆万柒仟捌佰玖拾元零壹分", RMBUtil.convert(1234567890.01D));
        Assert.assertEquals("人民币玖拾玖亿玖仟玖佰玖拾玖万玖仟玖佰玖拾玖元壹角壹分", RMBUtil.convert(9999999999.11D));
        Assert.assertEquals("人民币伍拾陆万柒仟捌佰玖拾元玖角贰分", RMBUtil.convert(567890.92D));
        Assert.assertEquals("人民币壹佰元叁角玖分", RMBUtil.convert(100.39D));
        Assert.assertEquals("人民币壹元贰角贰分", RMBUtil.convert(1.22D));
        Assert.assertEquals("人民币壹元贰角", RMBUtil.convert(1.20D));
        Assert.assertEquals("人民币壹元零贰分", RMBUtil.convert(1.02D));
        Assert.assertEquals("人民币玖角玖分", RMBUtil.convert(0.99D));
        Assert.assertEquals("人民币壹角", RMBUtil.convert(0.1D));
        Assert.assertEquals("人民币壹分", RMBUtil.convert(0.01D));
    }
}
