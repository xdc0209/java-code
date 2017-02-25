package com.xdc.basic.api.thread;

import java.io.IOException;

import com.xdc.basic.api.thread.wait.WaitCondition;
import com.xdc.basic.api.thread.wait.WaitUtils;

public class InterruptTest3
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        String command = "ping -n 100 127.0.0.1";
        int exitValue = exec(command, 5000);
        System.out.println(String.format("Exec cmd[%s], exitValue[%s].", command, exitValue));
    }

    public static int exec(String command, long timeout) throws IOException
    {
        Process process = Runtime.getRuntime().exec(command);

        // 伪代码1：开启线程处理标准输出流，本例专注Thread.interrupt()的用法，此处不实现。
        // 伪代码2：开启线程处理标准错误流，本例专注Thread.interrupt()的用法，此处不实现。

        Integer exitValue = waitFor(process, timeout);
        if (exitValue == null)
        {
            // 执行超时，不再等待其返回值，因此进程无存在的必要，杀死进程，否则会残留，浪费资源
            process.destroy();
            System.err.println(String.format("Exec cmd[%s] time out.", command));
            return -1;
        }
        else
        {
            System.out.println(String.format("Exec cmd[%s] successfully.", command));
            return process.exitValue();
        }
    }

    public static Integer waitFor(Process process, long timeout)
    {
        class WaitProcessExitThread extends Thread
        {
            private Process process;

            private Integer exitValue;

            public WaitProcessExitThread(Process process)
            {
                this.process = process;
            }

            public Integer getExitValue()
            {
                return exitValue;
            }

            public void run()
            {
                if (process != null)
                {
                    try
                    {
                        exitValue = process.waitFor();
                    }
                    catch (InterruptedException e)
                    {
                        // ignore
                    }
                }
            }
        }

        final WaitProcessExitThread waitProcessExitThread = new WaitProcessExitThread(process);
        waitProcessExitThread.setName("WaitProcessExitThread");
        waitProcessExitThread.setDaemon(true);
        waitProcessExitThread.start();

        WaitUtils.waitConditionUtilTimeout(new WaitCondition()
        {
            public boolean evalCondition()
            {
                // 判断进程是否结束
                return waitProcessExitThread.getExitValue() != null;
            }

            public void waitConditionTimeout()
            {
                // 等待超时，向等待线程发中断信号，不再等待
                waitProcessExitThread.interrupt();
            }

        }, timeout);

        return waitProcessExitThread.getExitValue();
    }
}
