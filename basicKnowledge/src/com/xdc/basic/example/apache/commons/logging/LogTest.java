package com.xdc.basic.example.apache.commons.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogTest
{
    private static Log log = LogFactory.getLog(LogTest.class);

    public static void main(String[] args)
    {
        log.isFatalEnabled(); // true
        log.isErrorEnabled(); // true
        log.isWarnEnabled(); // true
        log.isInfoEnabled(); // true
        log.isDebugEnabled(); // false
        log.isTraceEnabled(); // false

        log.fatal("fatal");
        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
        log.trace("trace");
    }
}
