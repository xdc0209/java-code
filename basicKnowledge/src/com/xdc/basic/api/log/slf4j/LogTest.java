package com.xdc.basic.api.log.slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest
{
    private static Logger log = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args)
    {
        // log.isFatalEnabled(); // true
        // log.isErrorEnabled(); // true
        // log.isWarnEnabled(); // true
        // log.isInfoEnabled(); // true
        // log.isDebugEnabled(); // false
        // log.isTraceEnabled(); // false
        //
        // log.fatal("fatal.");
        // log.error("error.");
        // log.warn("warn.");
        // log.info("info");
        // log.debug("debug.");
        // log.trace("trace.");

        List<Person> nums = new ArrayList<Person>();
        for (int i = 0; i < 2; i++)
        {
            nums.add(new Person("xdc-" + i, i, false));
        }
        getTop3(nums);
    }

    public static List<Person> getTop3(List<Person> nums)
    {
        // 定位问题时可以打印线程调用堆栈
        // log.error("Get thread stack trace info.", new Throwable("No error occur. Just for getting thread stack trace info."));

        log.info("Get top 3 bigin.");
        List<Person> result = new ArrayList<Person>();
        result.addAll(nums);

        if (nums.size() < 3)
        {
            log.warn("The size of persons is " + nums.size() + " and < 3.");
        }

        log.debug("Persons are {}.", nums);

        Collections.sort(result, new Comparator<Person>()
        {
            @Override
            public int compare(Person o1, Person o2)
            {
                return o2.age - o1.age;
            }
        });

        if (result.size() > 3)
        {
            result = result.subList(0, 3);
        }

        log.debug("Top 3 of persons are {}.", result);

        log.info("Get top 3 end.");
        return result;
    }
}
