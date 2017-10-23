package com.xdc.basic.api.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;

public class DeadlockMonitor
{
    public static void main(String[] args)
    {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        long[] deadlockedThreadIds = threadMXBean.findDeadlockedThreads();
        if (ArrayUtils.isNotEmpty(deadlockedThreadIds))
        {
            List<ThreadInfo> deadlockedThreadInfos = new ArrayList<ThreadInfo>();

            ThreadInfo[] allThreadInfos = threadMXBean.dumpAllThreads(true, true);
            for (long deadlockedThreadId : deadlockedThreadIds)
            {
                for (ThreadInfo threadInfo : allThreadInfos)
                {
                    if (threadInfo.getThreadId() == deadlockedThreadId)
                    {
                        deadlockedThreadInfos.add(threadInfo);
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(SystemUtils.LINE_SEPARATOR);
            sb.append("Found java level deadlocks, java stack information for the deadlock threads:");
            sb.append(SystemUtils.LINE_SEPARATOR);
            sb.append("============================================================================");
            sb.append(SystemUtils.LINE_SEPARATOR);

            for (ThreadInfo threadInfo : deadlockedThreadInfos)
            {
                sb.append(threadInfo);
            }
            System.out.println(sb);
        }
    }
}
