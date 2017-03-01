package com.xdc.basic.api.apache.commons.exec.framwaork;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import com.xdc.basic.commons.codec.BytesUtil;

public class ExecTool
{
    // 获取命令行的编码：
    // (1)windows下获取cmd.exe的编码，打开命令行后能在属性里看到的。
    // (2)linux下获取当前shell(如bash)的编码，执行locale可以查看。
    private static final String CMD_ENCODING = System.getProperty("sun.jnu.encoding");

    public static ExecResult exec(ExecCommand execCommand)
    {
        // 创建执行命令行
        CommandLine commandLine = null;
        if (SystemUtils.IS_OS_UNIX)
        {
            commandLine = CommandLine.parse("sh");
            commandLine.addArguments(new String[] { "-c", execCommand.getCommand() }, false);
        }
        else if (SystemUtils.IS_OS_WINDOWS)
        {
            commandLine = CommandLine.parse("cmd");
            commandLine.addArguments(new String[] { "/c", execCommand.getCommand() }, false);
        }
        else
        {
            throw new RuntimeException("Unsupport OS type. Suppurt OS type:[UNIX-like, Windows].");
        }

        // 创建执执行器
        DefaultExecutor executor = new DefaultExecutor();

        // 设置标准输出流，标准错误留，标准输入流。值得注意的是ByteArrayInputStream和ByteArrayOutputStream是不用关闭的，因为不是它们不是真正的IO流，未与设备交互，不过是Java内部缓冲用的。其实查看他的的close方法，是空实现的。
        ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
        ByteArrayOutputStream stdErr = new ByteArrayOutputStream();
        ByteArrayInputStream stdIn = null;
        if (StringUtils.isNotEmpty(execCommand.getStdIn()))
        {
            stdIn = new ByteArrayInputStream(BytesUtil.string2Bytes(execCommand.getStdIn(), CMD_ENCODING));
        }
        executor.setStreamHandler(new PumpStreamHandler(stdOut, stdErr, stdIn));

        // 设置看门狗，超时后，再等待10秒钟，杀死命令行进程，否则会残留，浪费资源。等10秒的原因是为了区分正常返回和执行超时。
        // 因为杀死进程后，进程也是有返回值的，看起来与执行完成没有区别。虽然执行成功（惯例是0）、失败、超时被杀死的返回码不同，也是不能根据返回值判断是否超时的，那样代码就跟操作系统的耦合了。
        ExecuteWatchdog watchdog = new ExecuteWatchdog(execCommand.getTimeout() + 10 * 1000L);
        executor.setWatchdog(watchdog);

        // 设置执行目录
        executor.setWorkingDirectory(execCommand.getWorkingDirectory());

        // 设置期望返回值，不符合抛异常ExecuteException。这里置为空，禁用此功能，由业务自己处理返回值，无需业务捕获异常。
        executor.setExitValues(null);

        // 设置关闭钩子，当jvm退出时，杀死正在执行的命令行进程。
        executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());

        // 创建执行结果处理器
        DefaultExecuteResultHandler defaultExecuteResultHandler = new DefaultExecuteResultHandler();

        try
        {
            // 执行命令
            executor.execute(commandLine, execCommand.getEnvironment(), defaultExecuteResultHandler);

            // 同步等待命令行进程完成
            defaultExecuteResultHandler.waitFor(execCommand.getTimeout());
        }
        catch (Throwable e)
        {
            // 执行失败，获取异常
            ExecResult execResult = new ExecResult(execCommand.getExpectedExitValues(), ExecResult.EXEC_FAILURE,
                    BytesUtil.bytes2String(stdOut.toByteArray(), CMD_ENCODING),
                    BytesUtil.bytes2String(stdErr.toByteArray(), CMD_ENCODING));
            execResult.setThrowable(e);

            return execResult;
        }

        if (defaultExecuteResultHandler.hasResult())
        {
            // 执行完成，获取返回值
            ExecResult execResult = new ExecResult(execCommand.getExpectedExitValues(),
                    defaultExecuteResultHandler.getExitValue(),
                    BytesUtil.bytes2String(stdOut.toByteArray(), CMD_ENCODING),
                    BytesUtil.bytes2String(stdErr.toByteArray(), CMD_ENCODING));

            execResult.setThrowable(defaultExecuteResultHandler.getException());

            return execResult;
        }
        else
        {
            // 执行超时，返回值设置为空
            return new ExecResult(execCommand.getExpectedExitValues(), ExecResult.EXEC_TIMEOUT,
                    BytesUtil.bytes2String(stdOut.toByteArray(), CMD_ENCODING),
                    BytesUtil.bytes2String(stdErr.toByteArray(), CMD_ENCODING));
        }
    }
}
