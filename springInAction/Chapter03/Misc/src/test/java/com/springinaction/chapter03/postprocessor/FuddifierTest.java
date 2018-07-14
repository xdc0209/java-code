package com.springinaction.chapter03.postprocessor;

import junit.framework.TestCase;

public class FuddifierTest extends TestCase
{
    public void testFuddify()
    {
        Fuddifier fuddifier = new Fuddifier();
        assertEquals("Wascawwy wabbit", fuddifier.fuddify("Rascally rabbit"));
    }

    public void testPostProcessAfterInitialization()
    {
        TestBean testBean = setupTestBean();

        Fuddifier fuddifier = new Fuddifier();
        fuddifier.postProcessAfterInitialization(testBean, null);

        assertEquals("Wascawwy wabbit", testBean.getSomeString());
        assertEquals(42, testBean.getSomeNumber());
    }

    private TestBean setupTestBean()
    {
        TestBean testBean = new TestBean();
        testBean.setSomeNumber(42);
        testBean.setSomeString("Rascally rabbit");
        return testBean;
    }

    public void testPostProcessBeforeInitialization()
    {
        // Shouldn't do anything
        TestBean testBean = setupTestBean();
        Fuddifier fuddifier = new Fuddifier();

        fuddifier.postProcessBeforeInitialization(testBean, null);

        assertEquals("Rascally rabbit", testBean.getSomeString());
        assertEquals(42, testBean.getSomeNumber());
    }

    private class TestBean
    {
        private int    someNumber;
        private String someString;

        public int getSomeNumber()
        {
            return someNumber;
        }

        public void setSomeNumber(int someNumber)
        {
            this.someNumber = someNumber;
        }

        public String getSomeString()
        {
            return someString;
        }

        public void setSomeString(String someString)
        {
            this.someString = someString;
        }
    }
}
