package com.xdc.basic.api.apache.commons.exec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;

public class ExecTest
{
    public static void main(String[] args)
    {
        String line = "ping -n 5 127.0.0.1";
        CommandLine cmdLine = CommandLine.parse(line);

        DefaultExecutor executor = getExecutor();

        ByteArrayOutputStream outAndErr = new ByteArrayOutputStream(); // 设置输出流和错误流
        ByteArrayInputStream input = new ByteArrayInputStream("y".getBytes()); // 设置输入流，比如删除文件要确认，当让这个例子用不到
        executor.setStreamHandler(new PumpStreamHandler(outAndErr, outAndErr, input));

        try
        {
            System.out.println("同步调用开始。");
            int exitValue = executor.execute(cmdLine);
            System.out.println("同步调用结束。");
            System.out.println("同步调用返回值：" + exitValue);

            System.out.println("异步调用开始。");
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            executor.execute(cmdLine, resultHandler);
            System.out.println("异步调用结束。");

            // 当前线程阻塞，直到命令行执行完成
            System.out.println("等待异步调用结果。");
            resultHandler.waitFor();
            exitValue = resultHandler.getExitValue();
            System.out.println("异步调用返回值：" + exitValue);
        }
        catch (ExecuteException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        try
        {
            // 获取cmd.exe的编码，打开命令行后能在属性里看到的。
            String cmdEncoding = System.getProperty("sun.jnu.encoding");
            byte[] buf = outAndErr.toByteArray();
            String string = new String(buf, cmdEncoding);
            // 这里会输出两次结果，因为同步异步各执行了一次，因此说明执行命令后要及时取出，免得混淆，或者干脆新建DefaultExecutor和ByteArrayOutputStream
            System.out.println(string);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    private static DefaultExecutor getExecutor()
    {
        DefaultExecutor executor = new DefaultExecutor();

        // 设置超时时间，超时后，ExecuteWatchdog.timeoutOccured(Watchdog w)调用Process.destroy()杀死命令行进程，类似于kill命令，但是此时命令行进程的返回值是不可信的，就是说无法确认是程序正常执行完毕，返回了一个值，还是因为超时返回了一个值。为了区分是否超时，可以配合使用DefaultExecuteResultHandler.waitFor(long timeout)进行区分，具体操作是：假设外界要求的超时时间是60秒，那么watchdog设置成60+10秒，waitFor参数设置成60秒，这样就能做到可以区分是否超时，并在超时后杀死命令行进程。
        // 通过测试发现：
        // (1)在window上：命令行进程被Process.destroy()杀死，返回值为1；在任务管理器被结束进程杀死，返回值为1。
        // (2)在linux(suse)上：命令行进程被Process.destroy()杀死，返回值为143；在任务管理器被结束进程杀死，返回值为137。
        ExecuteWatchdog watchdog = new ExecuteWatchdog(20 * 1000L);
        executor.setWatchdog(watchdog);

        // 设置期望返回值，不符合抛异常ExecuteException。这里置为空，禁用此功能，由业务自己处理返回值，无需业务捕获异常。
        executor.setExitValues(null);

        // 设置关闭钩子，当jvm退出时，杀死正在执行的命令行进程
        executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());

        return executor;
    }
}
