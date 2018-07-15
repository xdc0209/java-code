package com.xdc.basic.tools.statemachine.test;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

public class StateMachineFrameWorkTestHelper
{
    public static void assertStringListEqual(List<String> exp, List<String> rst)
    {
        try
        {
            if (exp.size() != rst.size())
            {
                System.out.println("exp size:" + exp.size() + " result size:" + rst.size());
                Assert.fail();
            }

            Iterator<String> it1 = exp.iterator();
            Iterator<String> it2 = rst.iterator();
            boolean foundError = false;
            while (it1.hasNext())
            {
                String expOne = it1.next();
                String rstOne = it2.next();
                if (!expOne.equals(rstOne))
                {
                    foundError = true;
                    break;
                }
            }
            if (foundError)
            {
                System.out.println("exp:" + exp);
                System.out.println("rst:" + rst);
                Assert.fail();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
