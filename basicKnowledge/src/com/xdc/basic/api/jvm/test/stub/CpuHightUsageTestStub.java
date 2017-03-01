package com.xdc.basic.api.jvm.test.stub;

/**
 * 模拟单个cpu的100%占用
 */
public class CpuHightUsageTestStub
{
    @SuppressWarnings("unused")
    public static void main(String[] args) throws InterruptedException
    {
        while (true)
        {
            long currentTimeMillis = System.currentTimeMillis();
            double sqrt = Math.sqrt(currentTimeMillis);
            double sin = Math.sin(sqrt);
        }
    }
}