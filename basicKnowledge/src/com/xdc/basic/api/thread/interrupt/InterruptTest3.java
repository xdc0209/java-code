package com.xdc.basic.api.thread.interrupt;

import java.io.IOException;

import com.xdc.basic.api.thread.wait.WaitCondition;
import com.xdc.basic.api.thread.wait.WaitUtils;

/**
 * 本例主要展示中断的用法，对于命令行的数据流采用伪代码方式说明。
 */
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
            // 返回值为空，说明是命令行进程执行超时导致的。因为未在我们要求的时间内返回有效的数据，因此命令行进程无存在的必要，要杀死进程，否则会残留，浪费资源。
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
        /**
         * 因为Process.waitFor()是同步阻塞方法，并且不支持设置超时，给编程开发造成了很大困扰。
         * 对于通用的框架(像socket，命令行调用)来说，超时机制是必须的。因为外界的状态是无法控制的，当外界因为代码bug或其他原因未返回，会导致我们的调用被同步的挂死，因此如果外界无法再有效时间返回，我们必须有能力中断这种挂死。
         * 此线程类用于实现命令行调用的超时机制。
         */
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
                        System.out.println("1-" + Thread.currentThread().isInterrupted());

                        // 等待命令行进程结束，结束阻塞的情况有两个：
                        // (1)命令行进程执行完毕，此时exitValue会被正常赋值。
                        // (2)其他线程对此线程设置了中断位，使此线程处于中断状态，process.waitFor()检测到此中断后，抛出异常InterruptedException，此时exitValue不会被赋值，还是为空的。
                        exitValue = process.waitFor();

                        System.out.println("2-" + Thread.currentThread().isInterrupted());
                    }
                    catch (InterruptedException e)
                    {
                        // ignore
                        System.out.println("3-" + Thread.currentThread().isInterrupted());
                    }
                    finally
                    {
                        // 参考自：int org.apache.commons.exec.DefaultExecutor.executeInternal(CommandLine command, Map environment, File dir, ExecuteStreamHandler streams) throws IOException
                        // see http://bugs.sun.com/view_bug.do?bug_id=6420270
                        // see https://issues.apache.org/jira/browse/EXEC-46
                        // Process.waitFor should clear interrupt status when throwing InterruptedException
                        // but we have to do that manually
                        // Process.waitFor响应了中断后，应该清除中断，但是它没有，因此我们必须负责清理。
                        // 一般的处理中断的策略是：在代码的合适位置(while死循环中的某处)，判断中断状态，如果处于中断，则先要清除中断，再对此次中断做对应的逻辑处理(如终止while死循环)。清除中断的原因是避免外界的一次异常中断信号，触发本线程的多次处理。
                        // 其实java的此问题已经修改，查看int java.lang.ProcessImpl.waitFor() throws InterruptedException中已经清除了中断后再抛出InterruptedException，此处再清除一次中断为了兼容有问题的jre，并且再中断一次也没有副作用。
                        Thread.interrupted();
                    }

                    System.out.println("4-" + Thread.currentThread().isInterrupted());
                }
            }
        }

        final WaitProcessExitThread waitProcessExitThread = new WaitProcessExitThread(process);
        waitProcessExitThread.setName("WaitCommandProcessExitThread");
        waitProcessExitThread.setDaemon(true);
        waitProcessExitThread.start();

        WaitUtils.waitConditionUtilTimeout(new WaitCondition()
        {
            public boolean evalCondition()
            {
                // 判断与命令行进程对应的等待线程是否存在返回值，进而判断命令行进程是否结束
                return waitProcessExitThread.getExitValue() != null;
            }

            public void waitConditionTimeout()
            {
                // 等待超时，向与命令行进程对应的等待线程发中断信号，不再等待
                waitProcessExitThread.interrupt();
            }

        }, timeout);

        return waitProcessExitThread.getExitValue();
    }
}
