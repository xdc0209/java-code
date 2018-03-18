package com.xdc.basic.commons.time;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DateUitlTest
{
    @Test
    @SuppressWarnings("deprecation")
    void test()
    {
        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 11 - 1, 29)));
        Assert.assertEquals(true, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 11 - 1, 30)));
        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 1)));

        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 23)));
        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 24)));
        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 25)));

        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 30)));
        Assert.assertEquals(true, DateUitl.isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 31)));
        Assert.assertEquals(false, DateUitl.isLastDayOfMonth(new Date(2017 - 1900, 1 - 1, 1)));

        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 11 - 1, 25)));
        Assert.assertEquals(true, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 11 - 1, 26)));
        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 11 - 1, 27)));

        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 23)));
        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 24)));
        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 25)));

        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 30)));
        Assert.assertEquals(true, DateUitl.isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 31)));
        Assert.assertEquals(false, DateUitl.isLastSaturdayOfMonth(new Date(2017 - 1900, 1 - 1, 1)));
    }
}
